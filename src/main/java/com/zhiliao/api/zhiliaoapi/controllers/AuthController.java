package com.zhiliao.api.zhiliaoapi.controllers;

import com.zhiliao.api.zhiliaoapi.httpObjects.AuthResponse;
import com.zhiliao.api.zhiliaoapi.httpObjects.LoginRequest;
import com.zhiliao.api.zhiliaoapi.models.User;
import com.zhiliao.api.zhiliaoapi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        String mobile = loginRequest.getMobile();
        String passwordInPlaintext = loginRequest.getPassword();

        Optional<User> user = authService.login(mobile, passwordInPlaintext);

        AuthResponse authResponse = new AuthResponse();
        if (user.isPresent()) {
            User actualUser = user.get();
            authResponse.setId(actualUser.getId());
            authResponse.setLoggedIn(true);
            authResponse.setName(actualUser.getName());
            authResponse.setMobile(actualUser.getMobile());
            authResponse.setToken(authService.generateToken(actualUser));
        } else {
            authResponse.setLoggedIn(false);
        }
        return authResponse;
    }
}
