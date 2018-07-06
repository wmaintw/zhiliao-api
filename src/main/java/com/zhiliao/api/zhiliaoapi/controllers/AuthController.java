package com.zhiliao.api.zhiliaoapi.controllers;

import com.zhiliao.api.zhiliaoapi.exceptions.BadParametersException;
import com.zhiliao.api.zhiliaoapi.exceptions.RegisterFailedException;
import com.zhiliao.api.zhiliaoapi.httpObjects.AuthRequest;
import com.zhiliao.api.zhiliaoapi.httpObjects.AuthResponse;
import com.zhiliao.api.zhiliaoapi.httpObjects.RegisterRequest;
import com.zhiliao.api.zhiliaoapi.httpObjects.RegisterResponse;
import com.zhiliao.api.zhiliaoapi.models.Consultant;
import com.zhiliao.api.zhiliaoapi.services.AuthService;
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

        Consultant consultant = authService.login(mobile, passwordInPlaintext);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(consultant.getId());
        authResponse.setName(consultant.getName());
        authResponse.setMobile(consultant.getMobile());
        authResponse.setToken(authService.generateToken());
        return authResponse;
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) throws RegisterFailedException {
        Consultant consultantRegistered = authService.register(registerRequest.getMobile(), registerRequest.getPassword());

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(consultantRegistered.getId());
        registerResponse.setMobile(consultantRegistered.getMobile());
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
