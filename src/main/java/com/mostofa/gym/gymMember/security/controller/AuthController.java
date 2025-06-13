package com.mostofa.gym.gymMember.security.controller;

import com.mostofa.gym.gymMember.security.dto.LoginRequest;
import com.mostofa.gym.gymMember.security.service.AuthService;
import com.mostofa.gym.gymMember.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return new ApiResponse(false, "Email is required");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return new ApiResponse(false, "Password is required");
        }
        return authService.authenticate(request.getEmail(), request.getPassword());
    }

}
