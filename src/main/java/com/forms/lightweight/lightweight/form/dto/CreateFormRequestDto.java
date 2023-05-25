package com.forms.lightweight.lightweight.form.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFormRequestDto {

    @NotNull
    private String title;

    @NotNull
    private String description;
}
