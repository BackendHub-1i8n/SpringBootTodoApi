package org.task.springboottodoapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.task.springboottodoapi.repository.TodoRepository;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;
    private String registrationDate;
    private String password;
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoModel> todos;

    @PrePersist
    protected void onCreate() {
        this.registrationDate = java.time.LocalDate.now().toString();
    }
}
