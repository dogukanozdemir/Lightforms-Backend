package com.forms.lightweight.lightweight.form.question.entity;

import com.forms.lightweight.lightweight.form.question.enums.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Column(columnDefinition = "SMALLINT UNSIGNED")
    private Integer questionOrder;

    private Long formId;
}
