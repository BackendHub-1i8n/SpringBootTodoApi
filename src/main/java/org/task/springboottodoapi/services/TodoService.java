package org.task.springboottodoapi.services;

import org.springframework.stereotype.Service;
import org.task.springboottodoapi.model.Todo;
import org.task.springboottodoapi.utils.DummyDatabase;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {
    private final DummyDatabase dummyDatabase;


    public TodoService(DummyDatabase dummyDatabase) {
        this.dummyDatabase = dummyDatabase;
    }

    public List<Todo> getAllTodos() {
        return dummyDatabase.findAll();
    }

    public Todo getTodoById(long id) {
        return dummyDatabase.findById(id);
    }

    public String createTodo(Todo todo) {
        return dummyDatabase.save(todo);
    }

    public String updateTodo(long id, Todo todo) {
        return dummyDatabase.update(id, todo);
    }

    public String deleteTodo(long id) {
        return dummyDatabase.delete(id);
    }

    public String onComplete(long id) {
        return dummyDatabase.complete(id);
    }

}
