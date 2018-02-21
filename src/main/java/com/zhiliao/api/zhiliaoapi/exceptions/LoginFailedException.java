package com.zhiliao.api.zhiliaoapi.exceptions;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
}
