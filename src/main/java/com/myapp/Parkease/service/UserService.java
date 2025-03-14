package com.myapp.Parkease.service;

import com.myapp.Parkease.dto.LoginRequest;
import com.myapp.Parkease.dto.LoginResponse;
import com.myapp.Parkease.entity.User;
import com.myapp.Parkease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // In a production app, you should use proper password encoding
            // This is a simple example comparing plain text passwords
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return new LoginResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    "Login successful",
                    true
                );
            } else {
                return new LoginResponse(
                    null,
                    null,
                    loginRequest.getEmail(),
                    "Invalid password",
                    false
                );
            }
        } else {
            return new LoginResponse(
                null,
                null,
                loginRequest.getEmail(),
                "User not found",
                false
            );
        }
    }
}