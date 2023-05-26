package com.forms.lightweight.lightweight.form.question.repository;
import com.forms.lightweight.lightweight.form.question.entity.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

    @Query(value = "SELECT MAX(q.questionOrder) FROM Question q WHERE q.formId = :formId")
    Integer findMaxOrder(Long formId);

}
