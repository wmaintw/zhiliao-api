package com.zhiliao.api.zhiliaoapi.intg.controllers;

import com.zhiliao.api.zhiliaoapi.dao.ConsultantDAO;
import com.zhiliao.api.zhiliaoapi.httpObjects.*;
import com.zhiliao.api.zhiliaoapi.intg.common.ControllerTestBase;
import com.zhiliao.api.zhiliaoapi.intg.common.TestDataHelper;
import com.zhiliao.api.zhiliaoapi.models.Consultant;
import com.zhiliao.api.zhiliaoapi.utils.SecurityHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.zhiliao.api.zhiliaoapi.utils.CustomizedErrorCode.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.*;

public class AuthControllerTest extends ControllerTestBase {
    @Autowired
    private ConsultantDAO consultantDAO;

    @Autowired
    private TestDataHelper dataHelper;

    private final String AUTH_URL = "/auth";
    private final String REGISTER_URL = "/auth/register";
    private final String MOBILE = "13900001111";
    private final String PASSWORD = "123";

    @Before
    public void setUp() throws Exception {
        consultantDAO.deleteAll();
    }

    @Test
    public void shouldLoginSuccessfully() throws Exception {
        dataHelper.createConsultant(MOBILE, PASSWORD);

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
        dataHelper.createConsultant(MOBILE, PASSWORD);

        AuthRequest authRequest = new AuthRequest("99988887777", PASSWORD);
        ResponseEntity<CustomizedError> error = restTemplate.postForEntity(AUTH_URL, authRequest, CustomizedError.class);

        assertThat(error.getStatusCode(), is(FORBIDDEN));
        assertThat(error.getBody().getCode(), is(LOGIN_FAILED));
        assertThat(error.getBody().getMessage(), is("Invalid credential."));
    }

    @Test
    public void shouldReturnOKStatusEvenLoginFailedGivenInvalidParam() throws Exception {
        dataHelper.createConsultant(MOBILE, PASSWORD);

        AuthRequest authRequest = new AuthRequest("", PASSWORD);
        ResponseEntity<CustomizedError> error = restTemplate.postForEntity(AUTH_URL, authRequest, CustomizedError.class);

        assertThat(error.getStatusCode(), is(BAD_REQUEST));
        assertThat(error.getBody().getCode(), is(BAD_REQUEST_OR_PARAM));
        assertThat(error.getBody().getMessage(), is("Mobile can not be empty."));
    }

    @Test
    public void shouldLoginFailedGivenWrongPassword() throws Exception {
        dataHelper.createConsultant(MOBILE, PASSWORD);

        AuthRequest authRequest = new AuthRequest(MOBILE, "wrong password");
        ResponseEntity<CustomizedError> error = restTemplate.postForEntity(AUTH_URL, authRequest, CustomizedError.class);

        assertThat(error.getStatusCode(), is(FORBIDDEN));
        assertThat(error.getBody().getCode(), is(LOGIN_FAILED));
        assertThat(error.getBody().getMessage(), is("Invalid credential."));
    }

    @Test
    public void shouldRegisterConsultantSuccessfully() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setMobile(MOBILE);
        registerRequest.setPassword(PASSWORD);

        ResponseEntity<RegisterResponse> registerResponse =
                restTemplate.postForEntity(REGISTER_URL, registerRequest, RegisterResponse.class);

        assertThat(registerResponse.getStatusCode(), is(OK));
        assertThat(registerResponse.getBody().getId(), greaterThan(0));
        assertThat(registerResponse.getBody().getMobile(), is(MOBILE));
        assertThat(registerResponse.getBody().getToken().length(), is(32));

        Optional<Consultant> registeredConsultant = consultantDAO.findOne(MOBILE, SecurityHelper.hash(PASSWORD));
        assertThat(registeredConsultant.isPresent(), is(true));
    }

    @Test
    public void shouldRegisterConsultantFailedGivenMobileExists() throws Exception {
        dataHelper.createConsultant(MOBILE, PASSWORD);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setMobile(MOBILE);
        registerRequest.setPassword("any password");

        ResponseEntity<CustomizedError> error =
                restTemplate.postForEntity(REGISTER_URL, registerRequest, CustomizedError.class);

        assertThat(error.getStatusCode(), is(OK));
        assertThat(error.getBody().getCode(), is(REGISTER_FAILED));
        assertThat(error.getBody().getMessage(), is("consultant already registered."));
    }
}
