package com.forms.lightweight.lightweight.form.question.dto;

import com.forms.lightweight.lightweight.form.question.enums.QuestionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddQuestionRequestDto {

    @NotNull
    private String title;

    @NotNull
    private QuestionType questionType;

}
