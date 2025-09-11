package org.task.springboottodoapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.springboottodoapi.dto.UserDto;
import org.task.springboottodoapi.model.UserModel;
import org.task.springboottodoapi.services.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserModel> getUser(@CookieValue UUID id, @CookieValue String username) {
        return ResponseEntity.ok(userService.getProfile(id, username));
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(userService.createUser(userModel));
    }

    @PostMapping("/login")
    public ResponseEntity<UserModel> loginUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.loginUser(user));
    }

    @PutMapping
    public ResponseEntity<UserModel> updateUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody UserDto user) {
        userService.deleteUser(user);
        return ResponseEntity.ok("User deleted successfully");
    }

}
