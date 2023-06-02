package com.forms.lightweight.lightweight.form.questionoptions;

import com.forms.lightweight.lightweight.form.questionoptions.dto.AddQuestionOptionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services/forms/questions")
@RequiredArgsConstructor
public class QuestionOptionsController {

    private final QuestionOptionsService questionOptionsService;


    @PostMapping("/{id}/options")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOption(@PathVariable Long id,@Validated @RequestBody AddQuestionOptionRequestDto requestDto){
        questionOptionsService.addOptionToQuestion(id,requestDto);
    }


}
