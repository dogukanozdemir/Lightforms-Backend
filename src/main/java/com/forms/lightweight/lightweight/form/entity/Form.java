package com.forms.lightweight.lightweight.form.entity;


import com.forms.lightweight.lightweight.form.enums.FormState;
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
@Table(
        name = "form",
        indexes = {@Index(name = "idx_form_state_user_id", columnList = "formState, userId")}
)
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private FormState formState;


}
