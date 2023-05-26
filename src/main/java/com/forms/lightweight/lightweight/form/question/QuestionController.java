package com.forms.lightweight.lightweight.form.question;

import com.forms.lightweight.lightweight.form.question.dto.AddQuestionRequestDto;
import com.forms.lightweight.lightweight.form.question.dto.UpdateQuestionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services/forms/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void addQuestion(@RequestParam Long formId,
                            @Validated @RequestBody AddQuestionRequestDto requestDto)
    {
        questionService.addQuestion(formId,requestDto);
    }

    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateQuestion(@RequestParam Long id,
                            @Validated @RequestBody UpdateQuestionRequestDto requestDto)
    {
        questionService.updateQuestion(id,requestDto);
    }
}
