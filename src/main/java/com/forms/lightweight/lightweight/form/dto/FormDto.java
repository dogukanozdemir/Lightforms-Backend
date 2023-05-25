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
public class FormDto {

    private String title;
    private String description;
    private FormState formState;
}
