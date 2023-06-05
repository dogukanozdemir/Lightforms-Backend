package com.forms.lightweight.lightweight.form.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFormResponseDto {

    private Long id;

    private String title;

    private String formIdentifier;
}
