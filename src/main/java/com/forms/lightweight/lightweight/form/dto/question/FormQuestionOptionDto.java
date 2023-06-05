package com.forms.lightweight.lightweight.form.dto.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormQuestionOptionDto {

    private Long optionId;
    private String optionText;
}
