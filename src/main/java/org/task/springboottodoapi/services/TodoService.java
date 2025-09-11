package org.task.springboottodoapi.services;

import org.springframework.stereotype.Service;
import org.task.springboottodoapi.dto.TodoDto;
import org.task.springboottodoapi.mapper.TodoMapper;
import org.task.springboottodoapi.model.TodoModel;
import org.task.springboottodoapi.repository.TodoRepository;
import org.task.springboottodoapi.utils.DummyDatabase;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;


    public TodoService(TodoRepository todoRepository, TodoMapper todoMapper) {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
    }

    public List<TodoModel> getAllTodos() {
        return todoRepository.findAll();
    }

    public Optional<TodoModel> getTodoById(UUID id) {
        return todoRepository.findById(id);
    }

    public TodoModel createTodo(TodoDto todo) {
        TodoModel todoModel = todoMapper.toModel(todo);
        return todoRepository.save(todoModel);
    }

    public String updateTodo(UUID id, TodoModel todo) {
        TodoModel todoModelOptional = todoRepository.findById(id).orElseThrow(()-> new RuntimeException("Todo not found"));
        todoModelOptional.setTitle(todo.getTitle());
        todoModelOptional.setDescription(todo.getDescription());
        todoModelOptional.setPriority(todo.getPriority());
        todoModelOptional.setCompleted(todo.isCompleted());
        todoRepository.save(todoModelOptional);
        return "Todo updated successfully";
    }

    public String deleteTodo(UUID id) {
        todoRepository.deleteById(id);
        return "Todo deleted successfully";
    }

    public String onComplete(UUID id) {
        return todoRepository.findById(id).map(todo -> {
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
            return "Todo marked as completed";
        }).orElse("Todo not found");
    }

}
