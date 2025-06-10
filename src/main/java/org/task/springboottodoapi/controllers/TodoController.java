package org.task.springboottodoapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.task.springboottodoapi.model.Todo;
import org.task.springboottodoapi.services.TodoService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<Object> getTodos() {
        try {
            List<Todo> todos = todoService.getAllTodos();
            if (todos.isEmpty()) {
                return ResponseEntity.ok("No todos found.");
            }
            return ResponseEntity.ok(todos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Todo API is running smoothly!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            Todo todo = todoService.getTodoById(id);
            if (todo == null) {
                return ResponseEntity.status(404).body("Todo not found.");
            }
            return ResponseEntity.ok(todo);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTodo(@RequestBody Todo todo) {
        try {
            if (Objects.isNull(todo.getTitle()) || Objects.isNull(todo.getDescription())) {
                return ResponseEntity.badRequest().body("Title and description are required.");
            }
            String response = todoService.createTodo(todo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        try {

            Todo existingTodo = todoService.getTodoById(id);
            if (existingTodo == null) {
                return ResponseEntity.status(404).body("Todo not found.");
            } else if (Objects.isNull(todo.getDescription())) {
                todo.setDescription(existingTodo.getDescription());
            } else if (Objects.isNull(todo.getTitle())) {
                todo.setTitle(existingTodo.getTitle());
            }

            String response = todoService.updateTodo(id, todo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable Long id) {
        try {
            String response = todoService.deleteTodo(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Object> completeTodo(@PathVariable Long id) {
        try {
            String response = todoService.onComplete(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
