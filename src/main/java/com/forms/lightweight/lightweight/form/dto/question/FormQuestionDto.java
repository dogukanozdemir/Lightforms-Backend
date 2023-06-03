package com.forms.lightweight.lightweight.form.dto.question;

import com.forms.lightweight.lightweight.form.question.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormQuestionDto {

    private String title;

    private QuestionType questionType;

    private List<FormQuestionOptionDto> questionOptions;
}
