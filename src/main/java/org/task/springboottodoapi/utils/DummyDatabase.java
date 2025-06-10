package org.task.springboottodoapi.utils;

import org.springframework.stereotype.Component;
import org.task.springboottodoapi.model.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class DummyDatabase {
    static Logger logger = Logger.getLogger(DummyDatabase.class.getName());
    static DummyDatabase instance = null;
    private List<Todo> dataSource;
    private String template = """
            {
                "id": %d,
                "title": "%s",
                "description": "%s",
                "completed": %b,
                "createdAt": "%s",
                "updatedAt": "%s"
            }
            """;
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

    private DummyDatabase() {
        this.dataSource = new ArrayList<>();
    }

    public static DummyDatabase getInstance() {
        if (DummyDatabase.instance == null){
            logger.info("Creating a new instance of DummyDatabase");
            DummyDatabase.instance = new DummyDatabase();
        }
        return DummyDatabase.instance;
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
            this.dataSource.add(todo);
            return String.format(successTemplate, "Todo created successfully");
        }catch (Exception e){
            return String.format(errorTemplate, e.getMessage());
        }
    }
    public String update(long id, Todo todo) {
        Todo existingTodo = findById(id);
        if (existingTodo != null) {
            existingTodo.setTitle(todo.getTitle());
            existingTodo.setDescription(todo.getDescription());
            existingTodo.setCompleted(todo.isCompleted());
            existingTodo.setUpdatedAt(java.time.LocalDateTime.now().toString());
            return String.format(successTemplate, "Todo updated successfully");
        } else {
            return String.format(errorTemplate, "Todo not found");
        }
    }

    public String complete(long id) {
        Todo existingTodo = findById(id);
        if (existingTodo != null) {
            existingTodo.setCompleted(!existingTodo.isCompleted());
            existingTodo.setUpdatedAt(java.time.LocalDateTime.now().toString());
            return String.format(successTemplate, "Todo marked as completed");
        } else {
            return String.format(errorTemplate, "Todo not found");
        }
    }

    public String delete(long id) {
        Todo existingTodo = findById(id);
        if (existingTodo != null) {
            this.dataSource.remove(existingTodo);
            return String.format(successTemplate, "Todo deleted successfully");
        } else {
            return String.format(errorTemplate, "Todo not found");
        }
    }
}
