package com.forms.lightweight.lightweight.form.questionoptions.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddQuestionOptionRequestDto {

    @NotNull
    @NotBlank
    private String optionText;

}
