package com.forms.lightweight.lightweight.form.questionoptions;

import com.forms.lightweight.lightweight.form.questionoptions.dto.AddQuestionOptionRequestDto;
import com.forms.lightweight.lightweight.form.questionoptions.dto.AddQuestionOptionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services/forms/questions")
@RequiredArgsConstructor
public class QuestionOptionsController {

    private final QuestionOptionsService questionOptionsService;


    @PostMapping("/{id}/options")
    public ResponseEntity<AddQuestionOptionResponseDto> addOption(@PathVariable Long id,
                                                                  @Validated @RequestBody AddQuestionOptionRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(questionOptionsService.addOptionToQuestion(id,requestDto));
    }

    @DeleteMapping("/options/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestionOption(@PathVariable Long id){
        questionOptionsService.deleteQuestionOption(id);
    }



}
