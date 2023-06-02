package com.forms.lightweight.lightweight.form.dto;

import com.forms.lightweight.lightweight.form.dto.question.FormQuestionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormContentResponseDto {

    private String title;

    private String description;

    private List<FormQuestionDto> questions;
}
