package com.zhiliao.api.zhiliaoapi.controllers;

import com.zhiliao.api.zhiliaoapi.common.ControllerTestBase;
import com.zhiliao.api.zhiliaoapi.dao.UserDAO;
import com.zhiliao.api.zhiliaoapi.httpObjects.*;
import com.zhiliao.api.zhiliaoapi.models.User;
import com.zhiliao.api.zhiliaoapi.utils.SecurityHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.OK;

public class AuthControllerTest extends ControllerTestBase {
    @Autowired
    private UserDAO userDAO;

    private final String AUTH_URL = "/auth";
    private final String REGISTER_URL = "/auth/register";
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
        assertThat(authResponse.getBody().getMobile(), is(MOBILE));
        assertThat(authResponse.getBody().getToken(), not(nullValue()));
    }

    @Test
    public void shouldLoginFailedGivenWrongMobile() throws Exception {
        createUser();

        AuthRequest authRequest = new AuthRequest("wrong mobile", PASSWORD);
        ResponseEntity<CustomizedError> error = restTemplate.postForEntity(AUTH_URL, authRequest, CustomizedError.class);

        assertThat(error.getStatusCode(), is(OK));
        assertThat(error.getBody().getCode(), is(101));
        assertThat(error.getBody().getMessage(), is("Invalid credential."));
    }

    @Test
    public void shouldLoginFailedGivenWrongPassword() throws Exception {
        createUser();

        AuthRequest authRequest = new AuthRequest(MOBILE, "wrong password");
        ResponseEntity<CustomizedError> error = restTemplate.postForEntity(AUTH_URL, authRequest, CustomizedError.class);

        assertThat(error.getStatusCode(), is(OK));
        assertThat(error.getBody().getCode(), is(101));
        assertThat(error.getBody().getMessage(), is("Invalid credential."));
    }

    @Test
    public void shouldRegisterUserSuccessfully() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setMobile(MOBILE);
        registerRequest.setPassword(PASSWORD);

        ResponseEntity<RegisterResponse> registerResponse =
                restTemplate.postForEntity(REGISTER_URL, registerRequest, RegisterResponse.class);

        assertThat(registerResponse.getStatusCode(), is(OK));
        assertThat(registerResponse.getBody().getId(), greaterThan(0));
        assertThat(registerResponse.getBody().getMobile(), is(MOBILE));
        assertThat(registerResponse.getBody().getToken().length(), is(32));

        Optional<User> registeredUser = userDAO.findOne(MOBILE, SecurityHelper.hash(PASSWORD));
        assertThat(registeredUser.isPresent(), is(true));
    }

    @Test
    public void shouldRegisterUserFailedGivenMobileExists() throws Exception {
        createUser(MOBILE, PASSWORD);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setMobile(MOBILE);
        registerRequest.setPassword("any password");

        ResponseEntity<CustomizedError> error =
                restTemplate.postForEntity(REGISTER_URL, registerRequest, CustomizedError.class);

        assertThat(error.getStatusCode(), is(OK));
        assertThat(error.getBody().getCode(), is(100));
        assertThat(error.getBody().getMessage(), is("user already registered."));
    }

    private void createUser() throws NoSuchAlgorithmException {
        createUser(MOBILE, PASSWORD);
    }

    private void createUser(String mobile, String password) {
        userDAO.create(mobile, SecurityHelper.hash(password));
    }
}
