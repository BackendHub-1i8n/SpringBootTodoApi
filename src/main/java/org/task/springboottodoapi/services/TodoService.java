package org.task.springboottodoapi.services;

import org.springframework.stereotype.Service;
import org.task.springboottodoapi.model.Todo;
import org.task.springboottodoapi.utils.DummyDatabase;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {
    private final AtomicLong id = new AtomicLong();
    private final DummyDatabase dummyDatabase;


    public TodoService(DummyDatabase dummyDatabase) {
        this.dummyDatabase = dummyDatabase;
        // 'low' | 'medium' | 'high'
        List<Todo> todos = List.of(
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
        todos.forEach(item -> item.setId(id.incrementAndGet()));
        this.dummyDatabase.setData(todos);
    }

    public List<Todo> getAllTodos() {
        return dummyDatabase.findAll();
    }

    public Todo getTodoById(long id) {
        return dummyDatabase.findById(id);
    }

    public String createTodo(Todo todo) {
        todo.setId(id.incrementAndGet());
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
