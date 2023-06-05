package com.forms.lightweight.lightweight.form.question;

import com.forms.lightweight.lightweight.form.question.dto.AddQuestionRequestDto;
import com.forms.lightweight.lightweight.form.question.dto.AddQuestionResponseDto;
import com.forms.lightweight.lightweight.form.question.dto.UpdateQuestionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services/forms")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/{formId}/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AddQuestionResponseDto> addQuestion(@PathVariable Long formId,
                                                              @Validated @RequestBody AddQuestionRequestDto requestDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.addQuestion(formId,requestDto));
    }

    @PutMapping("/questions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateQuestion(@PathVariable Long id,
                            @Validated @RequestBody UpdateQuestionRequestDto requestDto)
    {
        questionService.updateQuestion(id,requestDto);
    }

    @DeleteMapping("/questions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
    }
}
