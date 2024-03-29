package com.forms.lightweight.lightweight.form.questionoptions;

import com.forms.lightweight.lightweight.form.question.QuestionService;
import com.forms.lightweight.lightweight.form.question.entity.Question;
import com.forms.lightweight.lightweight.form.question.enums.QuestionType;
import com.forms.lightweight.lightweight.form.questionoptions.dto.AddQuestionOptionRequestDto;
import com.forms.lightweight.lightweight.form.questionoptions.dto.AddQuestionOptionResponseDto;
import com.forms.lightweight.lightweight.form.questionoptions.entity.QuestionOptions;
import com.forms.lightweight.lightweight.form.questionoptions.repository.QuestionOptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionOptionsService {
    private final QuestionService questionService;
    private final QuestionOptionsRepository questionOptionsRepository;

    public AddQuestionOptionResponseDto addOptionToQuestion(Long questionId, AddQuestionOptionRequestDto optionRequestDto){
        Question question = questionService.findQuestion(questionId);
        if(!QuestionType.RADIO.equals(question.getQuestionType()) &&
                !QuestionType.DROPDOWN.equals(question.getQuestionType()) && !QuestionType.CHECKBOX.equals(question.getQuestionType())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "you can't add options to a question with type TEXT");
        }
        QuestionOptions questionOption = QuestionOptions.builder()
                .optionValue(optionRequestDto.getOptionText())
                .questionId(questionId)
                .build();
        questionOptionsRepository.save(questionOption);
        return AddQuestionOptionResponseDto.builder()
                .id(questionOption.getId())
                .build();
    }

    public void deleteQuestionOption(Long id){
        questionOptionsRepository.findById(id)
                .ifPresentOrElse(
                        option -> questionOptionsRepository.deleteById(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    String.format("Option with id %s was not found", id));
                        });

    }

    public List<QuestionOptions> findQuestionOptions(Long id){
        return questionOptionsRepository.findQuestionOptionsByQuestionId(id);
    }




}
