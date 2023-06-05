package com.forms.lightweight.lightweight.form;

import com.forms.lightweight.lightweight.authentication.util.AuthUtil;
import com.forms.lightweight.lightweight.form.dto.*;
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

    public void createForm(CreateFormRequestDto createFormRequestDto) {
        UserEntity currentUser = authUtil.getCurrentUser();

        Form form = Form.builder()
                .title(createFormRequestDto.getTitle())
                .description(createFormRequestDto.getDescription())
                .formState(FormState.DRAFT)
                .userId(currentUser.getId())
                .formIdentifier(UUID.randomUUID().toString())
                .build();

        formRepository.save(form);
    }

    public void updateForm(Long id, UpdateFormRequestDto updateFormRequestDto) {
        Form form = formRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("form with id %s was not found", id)));
        form.setFormState(updateFormRequestDto.getFormState());
        form.setTitle(updateFormRequestDto.getTitle());
        form.setDescription(updateFormRequestDto.getDescription());
        formRepository.save(form);
    }

    public void deleteForm(Long id) {
        Form form = formRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("form with id %s was not found", id)));
        form.setFormState(FormState.DELETED);
        formRepository.save(form);
    }

    public PublishFormResponseDto publishForm(Long id){
        Form form = formRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("form with id %s was not found", id)));
        form.setFormState(FormState.PUBLISHED);
        formRepository.save(form);
        return PublishFormResponseDto.builder()
                .formIdentifier(form.getFormIdentifier())
                .build();
    }

    public List<FormPreviewResponseDto> getUserFormPreviewsByState(FormState state) {
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
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Form with id %s was not found", id)));

        List<FormQuestionDto> questions = questionService.findQuestions(id).stream()
                .map(question -> {
                    List<FormQuestionOptionDto> options = questionOptionsService.findQuestionOptions(question.getId()).stream()
                            .map(option -> FormQuestionOptionDto.builder()
                                    .optionId(option.getId())
                                    .optionText(option.getOptionValue())
                                    .build())
                            .collect(Collectors.toList());
                    return FormQuestionDto.builder()
                            .questionId(question.getId())
                            .title(question.getTitle())
                            .questionType(question.getQuestionType())
                            .questionOptions(options)
                            .build();
                })
                .collect(Collectors.toList());

        return FormContentResponseDto.builder()
                .title(form.getTitle())
                .description(form.getDescription())
                .questions(questions)
                .build();
    }

}
