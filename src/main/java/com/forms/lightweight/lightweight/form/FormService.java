package com.forms.lightweight.lightweight.form;

import com.forms.lightweight.lightweight.authentication.util.AuthUtil;
import com.forms.lightweight.lightweight.form.dto.CreateFormRequestDto;
import com.forms.lightweight.lightweight.form.dto.UpdateFormRequestDto;
import com.forms.lightweight.lightweight.form.entity.Form;
import com.forms.lightweight.lightweight.form.repository.FormRepository;
import com.forms.lightweight.lightweight.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;

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
                .isDeleted(createFormRequestDto.getIsDeleted())
                .isFavorite(createFormRequestDto.getIsFavorite())
                .user_id(currentUser.getId())
                .build();

        formRepository.save(form);
    }

    public void updateForm(Long id, UpdateFormRequestDto updateFormRequestDto){
        Form form = formRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("form with id %s was not found", id)));
        form.setTitle(updateFormRequestDto.getTitle());
        form.setDescription(updateFormRequestDto.getDescription());
        formRepository.save(form);
    }
}
