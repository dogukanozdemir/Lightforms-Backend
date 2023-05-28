package com.forms.lightweight.lightweight.user.authentication.repository;

import com.forms.lightweight.lightweight.user.authentication.entity.UserConfirmation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserConfirmationRepository extends CrudRepository<UserConfirmation, Long> {

    Optional<UserConfirmation> findByToken(String token);
}
