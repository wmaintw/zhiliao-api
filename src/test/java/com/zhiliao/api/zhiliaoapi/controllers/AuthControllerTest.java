package com.zhiliao.api.zhiliaoapi.controllers;

import com.zhiliao.api.zhiliaoapi.common.ControllerTestBase;
import com.zhiliao.api.zhiliaoapi.dao.UserDAO;
import com.zhiliao.api.zhiliaoapi.httpObjects.AuthRequest;
import com.zhiliao.api.zhiliaoapi.httpObjects.AuthResponse;
import com.zhiliao.api.zhiliaoapi.utils.SecurityHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class AuthControllerTest extends ControllerTestBase {
    @Autowired
    private UserDAO userDAO;

    @Before
    public void setUp() throws Exception {
        userDAO.deleteAll();
    }

    @Test
    public void shouldLoginSuccessfully() throws Exception {
        String mobile = "13900001111";
        String password = "123";

        userDAO.create(mobile, SecurityHelper.hash(password));

        AuthRequest authRequest = new AuthRequest();
        authRequest.setMobile(mobile);
        authRequest.setPassword(password);

        ResponseEntity<AuthResponse> authResponse = restTemplate.postForEntity("/auth", authRequest, AuthResponse.class);

        assertThat(authResponse, not(nullValue()));
        assertThat(authResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(authResponse.getBody().getMobile(), is(mobile));
    }
}
