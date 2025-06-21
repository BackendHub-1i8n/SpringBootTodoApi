package org.task.springboottodoapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Todo {
    private long id;
    private String title;
    private String description;
    private boolean completed;
    private String createdAt;
    private String updatedAt;
    private String priority;


    public Todo(String title, String description, String priority) {
        this.title = title;
        this.description = description;
        this.completed = false;
        this.createdAt = java.time.LocalDateTime.now().toString();
        this.updatedAt = java.time.LocalDateTime.now().toString();
        this.priority = priority;
    }

}
