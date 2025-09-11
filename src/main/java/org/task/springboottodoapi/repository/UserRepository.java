package org.task.springboottodoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.task.springboottodoapi.model.UserModel;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
}
