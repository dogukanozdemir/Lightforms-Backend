package com.forms.lightweight.lightweight.form;

import com.forms.lightweight.lightweight.authentication.util.AuthUtil;
import com.forms.lightweight.lightweight.form.dto.CreateFormRequestDto;
import com.forms.lightweight.lightweight.form.dto.FormContentResponseDto;
import com.forms.lightweight.lightweight.form.dto.FormPreviewResponseDto;
import com.forms.lightweight.lightweight.form.dto.UpdateFormRequestDto;
import com.forms.lightweight.lightweight.form.dto.question.FormQuestionDto;
import com.forms.lightweight.lightweight.form.dto.question.FormQuestionOptionDto;
import com.forms.lightweight.lightweight.form.entity.Form;
import com.forms.lightweight.lightweight.form.enums.FormState;
import com.forms.lightweight.lightweight.form.question.QuestionService;
import com.forms.lightweight.lightweight.form.questionoptions.QuestionOptionsService;
import com.forms.lightweight.lightweight.form.repository.FormRepository;
import com.forms.lightweight.lightweight.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormService {

    private final AuthUtil authUtil;
    private final FormRepository formRepository;
    private final QuestionService questionService;
    private final QuestionOptionsService questionOptionsService;
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

    public List<FormPreviewResponseDto> getUserFormPreviewsByState(FormState state){
        UserEntity currentUser = authUtil.getCurrentUser();
        return formRepository.findByFormStateAndAndUserId(state, currentUser.getId())
                .stream().map(
                        form -> FormPreviewResponseDto.builder()
                                .id(form.getId())
                                .title(form.getTitle())
                                .formState(form.getFormState())
                                .formIdentifier(form.getFormIdentifier())
                                .build()
                ).collect(Collectors.toList());
    }

    public FormContentResponseDto getFormContents(Long id){
        Form form = formRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("form with id %s was not found", id)));
        return FormContentResponseDto.builder()
                .title(form.getTitle())
                .description(form.getDescription())
                .questions(getFormQuestions(id))
                .build();
    }

    private List<FormQuestionDto> getFormQuestions(Long formId){
        return questionService.findQuestions(formId).stream()
                .map(
                        question -> FormQuestionDto.builder()
                                .title(question.getTitle())
                                .questionType(question.getQuestionType())
                                .questionOptions(getFormQuestionOptions(question.getId()))
                                .build()).collect(Collectors.toList());
    }

    private List<FormQuestionOptionDto> getFormQuestionOptions(Long questionId){
        return questionOptionsService.findQuestionOptions(questionId).stream()
                .map(questionOptions -> FormQuestionOptionDto.builder()
                        .optionText(questionOptions.getOptionValue())
                        .build())
                .collect(Collectors.toList());
    }


    public Form findFormById(Long id){
        return formRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("form with id %s was not found", id)));
    }
}
