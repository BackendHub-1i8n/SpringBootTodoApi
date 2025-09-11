package org.task.springboottodoapi.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.task.springboottodoapi.model.TodoModel;
import org.task.springboottodoapi.model.UserModel;
import org.task.springboottodoapi.utils.Priority;

import java.util.List;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<TodoModel, UUID> {
    List<TodoModel> findByCompleted(boolean completed);
    List<TodoModel> findByPriority(Priority priority);
    List<TodoModel> findByTitle(String title);
    List<TodoModel> findAllByUser(UserModel user, Sort sort);
}
