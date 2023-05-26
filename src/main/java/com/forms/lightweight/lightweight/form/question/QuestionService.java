package com.forms.lightweight.lightweight.form.question;
import com.forms.lightweight.lightweight.authentication.util.AuthUtil;
import com.forms.lightweight.lightweight.form.FormService;
import com.forms.lightweight.lightweight.form.entity.Form;
import com.forms.lightweight.lightweight.form.question.dto.AddQuestionRequestDto;
import com.forms.lightweight.lightweight.form.question.dto.UpdateQuestionRequestDto;
import com.forms.lightweight.lightweight.form.question.entity.Question;
import com.forms.lightweight.lightweight.form.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final FormService formService;
    private final QuestionRepository questionRepository;

    public void addQuestion(Long formId, AddQuestionRequestDto requestDto){
        formService.findFormById(formId);
        Integer currentQuestionOrder = questionRepository.findMaxOrder(formId) + 1;
        Question question = Question.builder()
                .title(requestDto.getTitle())
                .questionType(requestDto.getQuestionType())
                .formId(formId)
                .questionOrder(currentQuestionOrder)
                .build();
        questionRepository.save(question);
    }

    /* Question type Multiple Choice, Dropdown veya checkbox olarak add edilirse
    *  ve sonradan type'ı text olarak update edilirse DBde question_options table'ınde option value kalıyor
    *  bu gereksiz fazla data oluyor, yanlış associate edilebilir ve responselarda yanlış veriye sebep olabilir.
    *  TODO: FIX IT
    */
    public void updateQuestion(Long id, UpdateQuestionRequestDto requestDto){
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("question with id %s was not found", id)));
        question.setTitle(requestDto.getTitle());
        question.setQuestionType(requestDto.getQuestionType());

        questionRepository.save(question);
    }

    // TODO: IMPLEMENT QUESTION ORDER UPDATE
}
