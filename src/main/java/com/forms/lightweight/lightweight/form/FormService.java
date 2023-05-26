package com.forms.lightweight.lightweight.form;

import com.forms.lightweight.lightweight.authentication.util.AuthUtil;
import com.forms.lightweight.lightweight.form.dto.CreateFormRequestDto;
import com.forms.lightweight.lightweight.form.dto.FormDto;
import com.forms.lightweight.lightweight.form.dto.UpdateFormRequestDto;
import com.forms.lightweight.lightweight.form.entity.Form;
import com.forms.lightweight.lightweight.form.enums.FormState;
import com.forms.lightweight.lightweight.form.repository.FormRepository;
import com.forms.lightweight.lightweight.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormService {

    private final AuthUtil authUtil;
    private final FormRepository formRepository;
    public void createForm(CreateFormRequestDto createFormRequestDto){
        UserEntity currentUser = authUtil.getCurrentUser();

        Form form =  Form.builder()
                .title(createFormRequestDto.getTitle())
                .description(createFormRequestDto.getDescription())
                .formState(FormState.DRAFT)
                .userId(currentUser.getId())
                .formIdentifier(UUID.randomUUID().toString())
                .build();

        formRepository.save(form);
    }

    public void updateForm(Long id, UpdateFormRequestDto updateFormRequestDto){
        Form form = formRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("form with id %s was not found", id)));
        form.setFormState(updateFormRequestDto.getFormState());
        form.setTitle(updateFormRequestDto.getTitle());
        form.setDescription(updateFormRequestDto.getDescription());
        formRepository.save(form);
    }

    public void deleteForm(Long id){
        Form form = formRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("form with id %s was not found", id)));
        form.setFormState(FormState.DELETED);
        formRepository.save(form);
    }

    public List<FormDto> getUserFormsByFormState(FormState state){
        UserEntity currentUser = authUtil.getCurrentUser();
        return formRepository.findByFormStateAndAndUserId(state, currentUser.getId())
                .stream().map(
                        form -> FormDto.builder()
                                .id(form.getId())
                                .title(form.getTitle())
                                .description(form.getDescription())
                                .formState(form.getFormState())
                                .formIdentifier(form.getFormIdentifier())
                                .build()
                ).collect(Collectors.toList());
    }

    public Form findFormById(Long id){
        return formRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("form with id %s was not found", id)));
    }
}
