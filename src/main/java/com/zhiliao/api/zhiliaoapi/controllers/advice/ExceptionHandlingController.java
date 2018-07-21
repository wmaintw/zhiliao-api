package com.zhiliao.api.zhiliaoapi.controllers.advice;

import com.zhiliao.api.zhiliaoapi.exceptions.*;
import com.zhiliao.api.zhiliaoapi.httpObjects.CustomizedError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.zhiliao.api.zhiliaoapi.utils.CustomizedErrorCode.*;

@RestControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(ServerErrorException.class)
    @ResponseStatus(HttpStatus.OK)
    public CustomizedError onServerSideError(HttpServletRequest request, Throwable ex) {
        return new CustomizedError(SEVER_SIDE_ERROR, "An error happened.");
    }

    @ExceptionHandler(BadParametersException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomizedError onBadRequestOrParam(HttpServletRequest request, Throwable ex) {
        return new CustomizedError(BAD_REQUEST_OR_PARAM, ex.getMessage());
    }

    @ExceptionHandler(RegisterFailedException.class)
    @ResponseStatus(HttpStatus.OK)
    public CustomizedError onRegisterFailed(HttpServletRequest request, Throwable ex) {
        return new CustomizedError(REGISTER_FAILED, ex.getMessage());
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CustomizedError onLoginFailed(HttpServletRequest request, Throwable ex) {
        return new CustomizedError(LOGIN_FAILED, ex.getMessage());
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomizedError onRecordNotFound(HttpServletRequest request, Throwable ex) {
        return new CustomizedError(RECORD_NOT_FOUND, ex.getMessage());
    }
}
