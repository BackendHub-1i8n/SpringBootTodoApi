package org.task.springboottodoapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.springboottodoapi.dto.TodoDto;
import org.task.springboottodoapi.model.TodoModel;
import org.task.springboottodoapi.services.TodoService;
import org.task.springboottodoapi.utils.Priority;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<Object> getTodos() {
        try {
            List<TodoModel> todos = todoService.getAllTodos();
            if (todos.isEmpty()) {
                return ResponseEntity.ok("No todos found.");
            }
            return ResponseEntity.ok(todos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Todo API is running smoothly!");
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        try {
            TodoModel todo = todoService.getTodoById(id).orElse(null);
            if (todo == null) {
                return ResponseEntity.status(404).body("Todo not found.");
            }
            return ResponseEntity.ok(todo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTodo(@RequestBody TodoDto todo) {
        try {
            if (Objects.isNull(todo.getTitle()) || Objects.isNull(todo.getDescription())) {
                return ResponseEntity.badRequest().body("Title and description are required.");
            }
            if (todo.getPriority() == null) {
                todo.setPriority(Priority.LOW); // Default priority if not provided
            }
            TodoModel response = todoService.createTodo(todo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<String> updateTodo(@PathVariable UUID id, @RequestBody TodoModel todo) {
        try {

            return todoService.getTodoById(id).map(existingTodo -> {
                TodoModel updatedTodo = mergeTodo(existingTodo, todo);
                String response = todoService.updateTodo(id, updatedTodo);
                return ResponseEntity.ok(response);
            }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found."));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private TodoModel mergeTodo(TodoModel original, TodoModel incoming) {
        if (incoming.getTitle() == null) {
            incoming.setTitle(original.getTitle());
        }
        if (incoming.getDescription() == null) {
            incoming.setDescription(original.getDescription());
        }
        if (incoming.getPriority() == null) {
            incoming.setPriority(original.getPriority());
        }
        if (incoming.getCreatedAt() == null) {
            incoming.setCreatedAt(original.getCreatedAt());
        }
        if (incoming.getUser() == null) {
            incoming.setUser(original.getUser());
        }
        return incoming;
    }


    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> deleteTodo(@PathVariable UUID id) {
        try {
            String response = todoService.deleteTodo(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Object> completeTodo(@PathVariable UUID id) {
        try {
            String response = todoService.onComplete(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
