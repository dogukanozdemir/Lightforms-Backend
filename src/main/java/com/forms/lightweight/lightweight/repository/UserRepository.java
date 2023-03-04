package com.forms.lightweight.lightweight.repository;

import com.forms.lightweight.lightweight.dto.UserDTO;
import com.forms.lightweight.lightweight.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
}
