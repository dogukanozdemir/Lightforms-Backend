package com.forms.lightweight.lightweight.form.repository;

import com.forms.lightweight.lightweight.form.entity.Form;
import com.forms.lightweight.lightweight.form.enums.FormState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends CrudRepository<Form, Long> {

    List<Form> findByFormStateAndAndUserId(FormState state, Long userId);
}
