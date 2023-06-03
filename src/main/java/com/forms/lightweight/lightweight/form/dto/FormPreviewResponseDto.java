package com.forms.lightweight.lightweight.form.dto;

import com.forms.lightweight.lightweight.form.enums.FormState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormPreviewResponseDto {

    private Long id;
    private String title;
    private FormState formState;
    private String formIdentifier;
}
