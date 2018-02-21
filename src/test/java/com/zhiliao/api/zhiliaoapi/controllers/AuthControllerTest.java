package com.zhiliao.api.zhiliaoapi.controllers;

import com.zhiliao.api.zhiliaoapi.common.ControllerTestBase;
import com.zhiliao.api.zhiliaoapi.dao.UserDAO;
import com.zhiliao.api.zhiliaoapi.httpObjects.AuthRequest;
import com.zhiliao.api.zhiliaoapi.httpObjects.AuthResponse;
import com.zhiliao.api.zhiliaoapi.utils.SecurityHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.OK;

public class AuthControllerTest extends ControllerTestBase {
    private final String AUTH_URL = "/auth";
    @Autowired
    private UserDAO userDAO;
    private final String MOBILE = "13900001111";
    private final String PASSWORD = "123";

    @Before
    public void setUp() throws Exception {
        userDAO.deleteAll();
    }

    @Test
    public void shouldLoginSuccessfully() throws Exception {
        createUser();

        AuthRequest authRequest = new AuthRequest();
        authRequest.setMobile(MOBILE);
        authRequest.setPassword(PASSWORD);

        ResponseEntity<AuthResponse> authResponse = restTemplate.postForEntity(AUTH_URL, authRequest, AuthResponse.class);

        assertThat(authResponse, not(nullValue()));
        assertThat(authResponse.getStatusCode(), is(OK));
        assertThat(authResponse.getBody().isLoggedIn(), is(true));
        assertThat(authResponse.getBody().getMobile(), is(MOBILE));
        assertThat(authResponse.getBody().getToken(), not(nullValue()));
    }

    @Test
    public void shouldLoginFailedGivenWrongMobile() throws Exception {
        createUser();

        AuthRequest authRequest = new AuthRequest("wrong mobile", PASSWORD);
        ResponseEntity<AuthResponse> authResponse = restTemplate.postForEntity(AUTH_URL, authRequest, AuthResponse.class);

        assertThat(authResponse.getStatusCode(), is(OK));
        assertThat(authResponse.getBody().isLoggedIn(), is(false));
        assertThat(authResponse.getBody().getToken(), is(nullValue()));
    }

    @Test
    public void shouldLoginFailedGivenWrongPassword() throws Exception {
        createUser();

        AuthRequest authRequest = new AuthRequest(MOBILE, "wrong password");
        ResponseEntity<AuthResponse> authResponse = restTemplate.postForEntity(AUTH_URL, authRequest, AuthResponse.class);

        assertThat(authResponse.getStatusCode(), is(OK));
        assertThat(authResponse.getBody().isLoggedIn(), is(false));
        assertThat(authResponse.getBody().getToken(), is(nullValue()));
    }

    private void createUser() throws NoSuchAlgorithmException {
        createUser(MOBILE, PASSWORD);
    }

    private void createUser(String mobile, String password) throws NoSuchAlgorithmException {
        userDAO.create(mobile, SecurityHelper.hash(password));
    }
}
