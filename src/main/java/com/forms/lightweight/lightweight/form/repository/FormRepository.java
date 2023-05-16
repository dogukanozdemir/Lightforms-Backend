package com.forms.lightweight.lightweight.form.repository;

import com.forms.lightweight.lightweight.form.entity.Form;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends CrudRepository<Form, Long> {
}
