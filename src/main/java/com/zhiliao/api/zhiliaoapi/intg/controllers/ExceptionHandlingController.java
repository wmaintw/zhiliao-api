package com.zhiliao.api.zhiliaoapi.intg.controllers;

import com.zhiliao.api.zhiliaoapi.exceptions.LoginFailedException;
import com.zhiliao.api.zhiliaoapi.exceptions.RegisterFailedException;
import com.zhiliao.api.zhiliaoapi.exceptions.ServerErrorException;
import com.zhiliao.api.zhiliaoapi.httpObjects.CustomizedError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(ServerErrorException.class)
    @ResponseStatus(HttpStatus.OK)
    public CustomizedError onServerSideError(HttpServletRequest request, Throwable ex) {
        return new CustomizedError(500, "An error happened.");
    }

    @ExceptionHandler(RegisterFailedException.class)
    @ResponseStatus(HttpStatus.OK)
    public CustomizedError onRegisterFailed(HttpServletRequest request, Throwable ex) {
        return new CustomizedError(100, ex.getMessage());
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.OK)
    public CustomizedError onLoginFailed(HttpServletRequest request, Throwable ex) {
        return new CustomizedError(101, ex.getMessage());
    }
}
