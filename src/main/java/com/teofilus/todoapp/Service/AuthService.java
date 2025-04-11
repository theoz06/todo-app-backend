package com.teofilus.todoapp.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.teofilus.todoapp.DTOs.Request.AuthRequest;
import com.teofilus.todoapp.DTOs.Request.RegisterRequest;
import com.teofilus.todoapp.DTOs.Response.AuthResponse;
import com.teofilus.todoapp.Models.User;
import com.teofilus.todoapp.Repository.UserRepository;
import com.teofilus.todoapp.Utils.JwtUtils;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    public AuthResponse login (AuthRequest request) {
           Optional<User> user = userRepository.findByUsername(request.getUsername());
           if (user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                String token = jwtUtils.generateToken(request.getUsername());
                return new AuthResponse(token);
           }
           return new AuthResponse("Invalid Credentials");

    }

    public String register(RegisterRequest request){
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            return "User already exists";
        } else {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            return "User registered successfully";
        }
    }
}
