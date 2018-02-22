package com.zhiliao.api.zhiliaoapi.intg.controllers;

import com.zhiliao.api.zhiliaoapi.exceptions.BadParametersException;
import com.zhiliao.api.zhiliaoapi.exceptions.RegisterFailedException;
import com.zhiliao.api.zhiliaoapi.httpObjects.AuthRequest;
import com.zhiliao.api.zhiliaoapi.httpObjects.AuthResponse;
import com.zhiliao.api.zhiliaoapi.httpObjects.RegisterRequest;
import com.zhiliao.api.zhiliaoapi.httpObjects.RegisterResponse;
import com.zhiliao.api.zhiliaoapi.models.User;
import com.zhiliao.api.zhiliaoapi.unit.services.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        String mobile = authRequest.getMobile();
        String passwordInPlaintext = authRequest.getPassword();
        validateInput(mobile, passwordInPlaintext);

        User user = authService.login(mobile, passwordInPlaintext);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(user.getId());
        authResponse.setName(user.getName());
        authResponse.setMobile(user.getMobile());
        authResponse.setToken(authService.generateToken());
        return authResponse;
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) throws RegisterFailedException {
        User userRegistered = authService.register(registerRequest.getMobile(), registerRequest.getPassword());

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(userRegistered.getId());
        registerResponse.setMobile(userRegistered.getMobile());
        registerResponse.setToken(authService.generateToken());
        return registerResponse;
    }

    private void validateInput(String mobile, String passwordInPlaintext) {
        if (StringUtils.isEmpty(mobile)) {
            throw new BadParametersException("Mobile can not be empty.");
        }
        if (!StringUtils.isNumeric(mobile)) {
            throw new BadParametersException("Mobile number is invalid.");
        }
        if (StringUtils.isEmpty(passwordInPlaintext)) {
            throw new BadParametersException("Password can not be empty.");
        }
    }
}
