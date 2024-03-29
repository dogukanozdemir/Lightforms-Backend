package com.forms.lightweight.lightweight.form.question;
import com.forms.lightweight.lightweight.authentication.util.AuthUtil;
import com.forms.lightweight.lightweight.form.FormService;
import com.forms.lightweight.lightweight.form.entity.Form;
import com.forms.lightweight.lightweight.form.question.dto.AddQuestionRequestDto;
import com.forms.lightweight.lightweight.form.question.dto.AddQuestionResponseDto;
import com.forms.lightweight.lightweight.form.question.dto.UpdateQuestionRequestDto;
import com.forms.lightweight.lightweight.form.question.entity.Question;
import com.forms.lightweight.lightweight.form.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public AddQuestionResponseDto addQuestion(AddQuestionRequestDto requestDto){
        Integer maxOrder = questionRepository.findMaxOrder(requestDto.getFormId());
        Integer currentQuestionOrder = maxOrder != null ? maxOrder + 1 : 1;
        Question question = Question.builder()
                .title(requestDto.getTitle())
                .questionType(requestDto.getQuestionType())
                .formId(requestDto.getFormId())
                .questionOrder(currentQuestionOrder)
                .build();
        questionRepository.save(question);

        return AddQuestionResponseDto.builder()
                .id(question.getId())
                .build();
    }
    public void updateQuestion(Long id, UpdateQuestionRequestDto requestDto){
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("question with id %s was not found", id)));
        question.setTitle(requestDto.getTitle());

        questionRepository.save(question);
    }
    public Question findQuestion(Long id){
        return questionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("question with id %s was not found", id)));
    }

    public void deleteQuestion(Long id) {
        questionRepository.findById(id)
                .ifPresentOrElse(
                        question -> questionRepository.deleteById(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    String.format("Question with id %s was not found", id));
                        });
    }


    public List<Question> findQuestions(Long formId){
        return questionRepository.findQuestionsByFormId(formId);
    }
}
