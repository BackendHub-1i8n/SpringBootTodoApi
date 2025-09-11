package org.task.springboottodoapi.services;

import org.springframework.stereotype.Service;
import org.task.springboottodoapi.dto.UserDto;
import org.task.springboottodoapi.mapper.UserMapper;
import org.task.springboottodoapi.model.UserModel;
import org.task.springboottodoapi.repository.UserRepository;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserModel createUser(UserModel userModel) {
        try {
            return userRepository.save(userModel);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }

    public UserModel loginUser(UserDto userDto) {
        try {
            return userRepository.findAll().stream()
                    .filter(user -> user.getUsername().equals(userDto.getUsername()) &&
                                    user.getPassword().equals(userDto.getPassword()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        } catch (Exception e) {
            throw new RuntimeException("Error logging in: " + e.getMessage());
        }
    }

    public UserModel updateUser(UserDto userDto) {
        try {
            UserModel userModel = userRepository.findAll().stream()
                    .filter(user -> user.getUsername().equals(userDto.getUsername()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User not found"));
            userModel.setPassword(userDto.getPassword());
            return userRepository.save(userModel);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }
    }

    public  void deleteUser(UserDto userDto) {
        try {
            UserModel userModel = userRepository.findAll().stream()
                    .filter(user -> user.getUsername().equals(userDto.getUsername()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User not found"));
            userRepository.delete(userModel);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }
    }

    public UserModel getProfile(UUID id, String username) {
        try {
            return userRepository.findAll().stream()
                    .filter(user -> user.getUsername().equals(username) && user.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error fetching profile: " + e.getMessage());
        }
    }
}
