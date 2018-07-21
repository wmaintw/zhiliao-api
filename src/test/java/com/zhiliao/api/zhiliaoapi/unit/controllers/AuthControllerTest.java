package com.zhiliao.api.zhiliaoapi.unit.controllers;

import com.zhiliao.api.zhiliaoapi.exceptions.BadParametersException;
import com.zhiliao.api.zhiliaoapi.httpObjects.AuthRequest;
import com.zhiliao.api.zhiliaoapi.controllers.AuthController;
import org.junit.Test;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class AuthControllerTest {
    @Test(expected = BadParametersException.class)
    public void loginProcessShouldThrowExceptionGivenEmptyMobileIsSubmitted() throws Exception {
        AuthController authController = new AuthController();
        AuthRequest authRequest = new AuthRequest(EMPTY, "any password");
        authController.login(authRequest);
    }

    @Test(expected = BadParametersException.class)
    public void loginProcessShouldThrowExceptionGivenEmptyPasswordIsSubmitted() throws Exception {
        AuthController authController = new AuthController();
        AuthRequest authRequest = new AuthRequest("any mobile", EMPTY);
        authController.login(authRequest);
    }
}