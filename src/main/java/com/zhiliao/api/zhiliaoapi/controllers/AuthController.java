package com.zhiliao.api.zhiliaoapi.controllers;

import com.zhiliao.api.zhiliaoapi.httpObjects.AuthResponse;
import com.zhiliao.api.zhiliaoapi.httpObjects.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        String mobile = loginRequest.getMobile();
        String passwordInPlaintext = loginRequest.getPassword();

        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(100);
        authResponse.setLoggedIn(true);
        authResponse.setName("test user");
        authResponse.setMobile(mobile);
        authResponse.setToken("dzlknlkqnr90dsnkfo " + passwordInPlaintext);
        return authResponse;
    }
}
