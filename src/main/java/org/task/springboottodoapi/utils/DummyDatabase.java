package org.task.springboottodoapi.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.task.springboottodoapi.model.TodoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DummyDatabase {
    private List<TodoModel> dataSource;
    private String errorTemplate = """
            {
                "error": "%s"
            }
            """;

    private String successTemplate = """
            {
                "message": "%s"
            }
            """;
    private static final List<TodoModel> SAMPLE_TODOS = List.of(
            new TodoModel("Learn Spring Boot", "Complete the Spring Boot tutorial", Priority.HIGH),
            new TodoModel("Build a REST API", "Create a simple REST API using Spring Boot", Priority.MEDIUM),
            new TodoModel("Deploy to Heroku", "Deploy the Spring Boot application to Heroku", Priority.LOW),
            new TodoModel("Write Unit Tests", "Add unit tests for the Spring Boot application", Priority.MEDIUM),
            new TodoModel("Learn Docker", "Understand the basics of Docker and containerization", Priority.HIGH),
            new TodoModel("Explore Spring Security", "Implement basic authentication and authorization", Priority.MEDIUM),
            new TodoModel("Integrate with a Database", "Connect the Spring Boot application to a database", Priority.HIGH),
            new TodoModel("Implement Logging", "Set up logging for the Spring Boot application", Priority.LOW),
            new TodoModel("Create a Frontend", "Build a simple frontend using React or Angular", Priority.MEDIUM),
            new TodoModel("Learn about Microservices", "Understand the principles of microservices architecture", Priority.HIGH),
            new TodoModel("Explore Spring Cloud", "Learn about Spring Cloud for building distributed systems", Priority.MEDIUM),
            new TodoModel("Implement Caching", "Add caching to improve performance", Priority.LOW),
            new TodoModel("Learn about Asynchronous Processing", "Implement asynchronous processing in Spring Boot", Priority.MEDIUM),
            new TodoModel("Explore Spring Data JPA", "Use Spring Data JPA for database operations", Priority.HIGH));

    private DummyDatabase() {
        this.dataSource = new ArrayList<>();
    }

    public void setData(List<TodoModel> dataSource) {
        this.dataSource = dataSource;
    }

    public List<TodoModel> findAll() {
        return this.dataSource;
    }

    public TodoModel findById(UUID id) {
        return this.dataSource.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public String save(TodoModel todo) {
        try {
            this.dataSource.add(todo);
            return String.format(successTemplate, "Todo created successfully");
        }catch (Exception e){
            return String.format(errorTemplate, e.getMessage());
        }
    }
    public String update(UUID id, TodoModel todo) {
        TodoModel existingTodo = findById(id);
        if (!Objects.isNull(existingTodo)) {
            existingTodo.setTitle(todo.getTitle());
            existingTodo.setDescription(todo.getDescription());
            existingTodo.setCompleted(todo.isCompleted());
            existingTodo.setUpdatedAt(java.time.LocalDateTime.now());
            return String.format(successTemplate, "Todo updated successfully");
        } else {
            return String.format(errorTemplate, "Todo not found");
        }
    }

    public String complete(UUID id) {
        TodoModel existingTodo = findById(id);
        if (!Objects.isNull(existingTodo)) {
            existingTodo.setCompleted(!existingTodo.isCompleted());
            existingTodo.setUpdatedAt(java.time.LocalDateTime.now());
            return String.format(successTemplate, "Todo marked as completed");
        } else {
            return String.format(errorTemplate, "Todo not found");
        }
    }

    public String delete(UUID id) {
        TodoModel existingTodo = findById(id);
        if (!Objects.isNull(existingTodo)) {
            this.dataSource.remove(existingTodo);
            return String.format(successTemplate, "Todo deleted successfully");
        } else {
            return String.format(errorTemplate, "Todo not found");
        }
    }


    @PostConstruct
    public void init() {
        this.dataSource.addAll(SAMPLE_TODOS);
    }
}
