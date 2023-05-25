package com.forms.lightweight.lightweight.form.entity;


import com.forms.lightweight.lightweight.form.enums.FormState;
import com.forms.lightweight.lightweight.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private FormState formState;


}
