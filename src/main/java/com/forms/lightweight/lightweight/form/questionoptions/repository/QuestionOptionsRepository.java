package com.forms.lightweight.lightweight.form.questionoptions.repository;

import com.forms.lightweight.lightweight.form.questionoptions.entity.QuestionOptions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionOptionsRepository extends CrudRepository<QuestionOptions,Long> {

    List<QuestionOptions> findQuestionOptionsByQuestionId(Long id);
}
