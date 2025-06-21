package org.task.springboottodoapi.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.task.springboottodoapi.model.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Component
public class DummyDatabase {
    private List<Todo> dataSource;
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
    private final AtomicLong idCounter = new AtomicLong(0);
    private static final List<Todo> SAMPLE_TODOS = List.of(
            new Todo("Learn Spring Boot", "Complete the Spring Boot tutorial", "high"),
            new Todo("Build a REST API", "Create a simple REST API using Spring Boot", "medium"),
            new Todo("Deploy to Heroku", "Deploy the Spring Boot application to Heroku", "low"),
            new Todo("Write Unit Tests", "Add unit tests for the Spring Boot application", "medium"),
            new Todo("Learn Docker", "Understand the basics of Docker and containerization", "low"),
            new Todo("Explore Spring Security", "Implement basic authentication and authorization", "high"),
            new Todo("Integrate with a Database", "Connect the Spring Boot application to a database", "medium"),
            new Todo("Implement Logging", "Set up logging for the Spring Boot application", "low"),
            new Todo("Create a Frontend", "Build a simple frontend using React or Angular", "medium"),
            new Todo("Learn about Microservices", "Understand the principles of microservices architecture", "high"),
            new Todo("Explore Spring Cloud", "Learn about Spring Cloud for building distributed systems", "medium"),
            new Todo("Implement Caching", "Add caching to improve performance", "low"),
            new Todo("Learn about Asynchronous Processing", "Implement asynchronous processing in Spring Boot", "medium"),
            new Todo("Explore Spring Data JPA", "Use Spring Data JPA for database operations", "high"));

    private DummyDatabase() {
        this.dataSource = new ArrayList<>();
    }

    public void setData(List<Todo> dataSource) {
        this.dataSource = dataSource;
    }

    public List<Todo> findAll() {
        return this.dataSource;
    }

    public Todo findById(long id) {
        return this.dataSource.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public String save(Todo todo) {
        try {
            todo.setId(idCounter.incrementAndGet());
            this.dataSource.add(todo);
            return String.format(successTemplate, "Todo created successfully");
        }catch (Exception e){
            return String.format(errorTemplate, e.getMessage());
        }
    }
    public String update(long id, Todo todo) {
        Todo existingTodo = findById(id);
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

    public String complete(long id) {
        Todo existingTodo = findById(id);
        if (!Objects.isNull(existingTodo)) {
            existingTodo.setCompleted(!existingTodo.isCompleted());
            existingTodo.setUpdatedAt(java.time.LocalDateTime.now());
            return String.format(successTemplate, "Todo marked as completed");
        } else {
            return String.format(errorTemplate, "Todo not found");
        }
    }

    public String delete(long id) {
        Todo existingTodo = findById(id);
        if (!Objects.isNull(existingTodo)) {
            this.dataSource.remove(existingTodo);
            return String.format(successTemplate, "Todo deleted successfully");
        } else {
            return String.format(errorTemplate, "Todo not found");
        }
    }


    @PostConstruct
    public void init() {
        SAMPLE_TODOS.forEach(item -> item.setId(idCounter.incrementAndGet()));
        this.dataSource.addAll(SAMPLE_TODOS);
    }
}
