package com.forms.lightweight.lightweight.form.question;

import com.forms.lightweight.lightweight.form.question.dto.AddQuestionRequestDto;
import com.forms.lightweight.lightweight.form.question.dto.UpdateQuestionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services/forms")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/{formId}/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public void addQuestion(@PathVariable Long formId,
                            @Validated @RequestBody AddQuestionRequestDto requestDto)
    {
        questionService.addQuestion(formId,requestDto);
    }

    @PutMapping("/questions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateQuestion(@PathVariable Long id,
                            @Validated @RequestBody UpdateQuestionRequestDto requestDto)
    {
        questionService.updateQuestion(id,requestDto);
    }
}
