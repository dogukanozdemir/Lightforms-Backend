package com.forms.lightweight.lightweight.user.repository;

import com.forms.lightweight.lightweight.user.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u JOIN UserConfirmation uc ON u.id = uc.userId WHERE uc.token =:token")
    Optional<UserEntity> findUserByConfirmationToken(String token);
}
