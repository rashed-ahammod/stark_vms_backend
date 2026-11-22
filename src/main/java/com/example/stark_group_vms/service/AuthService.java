package com.example.stark_group_vms.service;

import com.example.stark_group_vms.dto.LoginRequest;
import com.example.stark_group_vms.dto.LoginResponse;
import com.example.stark_group_vms.dto.UserResponse;
import com.example.stark_group_vms.entity.User;
import com.example.stark_group_vms.exception.InvalidCredentialsException;
import com.example.stark_group_vms.exception.ResourceNotFoundException;
import com.example.stark_group_vms.repository.UserRepository;
import com.example.stark_group_vms.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        // The same message is returned whether the username or the password is
        // wrong, so the response cannot be used to discover valid usernames.
        User user = userRepository.findByUsernameIgnoreCase(request.username().trim())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(user);
        return LoginResponse.of(token, jwtService.getExpirySeconds(), UserResponse.from(user));
    }

    @Transactional(readOnly = true)
    public UserResponse getByUsername(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        return UserResponse.from(user);
    }
}
