package com.zhiliao.api.zhiliaoapi.intg.controllers;


import com.zhiliao.api.zhiliaoapi.controllers.VisitorController;
import com.zhiliao.api.zhiliaoapi.dao.VisitorDAO;
import com.zhiliao.api.zhiliaoapi.httpObjects.CustomizedError;
import com.zhiliao.api.zhiliaoapi.httpObjects.visitor.CreateVisitorRequest;
import com.zhiliao.api.zhiliaoapi.httpObjects.visitor.CreateVisitorResponse;
import com.zhiliao.api.zhiliaoapi.intg.common.ControllerTestBase;
import com.zhiliao.api.zhiliaoapi.models.Visitor;
import com.zhiliao.api.zhiliaoapi.utils.Gender;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static com.zhiliao.api.zhiliaoapi.utils.CustomizedErrorCode.RECORD_NOT_FOUND;
import static com.zhiliao.api.zhiliaoapi.utils.Gender.MAIL;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.*;

public class VisitorControllerTest extends ControllerTestBase {

    private VisitorController controller;

    @Autowired
    private VisitorDAO visitorDAO;

    @Before
    public void setUp() throws Exception {
        visitorDAO.deleteAll();

        controller = new VisitorController();
    }

    @Test
    public void shouldFindSingleVisitorGivenVisitorExistsInDb() throws Exception {
        String name = "小明";
        String mobile = "13900001111";
        Visitor visitorCreated = createVisitor(name, mobile);

        ResponseEntity<Visitor> visitorResponse = restTemplate.getForEntity("/visitors/" + visitorCreated.getId(), Visitor.class);

        assertThat(visitorResponse.getStatusCode(), is(OK));
        Visitor visitor = visitorResponse.getBody();

        assertThat(visitor.getRealName(), is(visitorCreated.getRealName()));
        assertThat(visitor.getMobile(), is(visitorCreated.getMobile()));
    }

    @Test
    public void shouldReturnErrorGivenVisitorNotExistsInDb() throws Exception {
        String notExistsVisitorId = "998877";

        ResponseEntity<CustomizedError> responseEntity = restTemplate.getForEntity("/visitors/" + notExistsVisitorId, CustomizedError.class);

        assertThat(responseEntity.getStatusCode(), is(NOT_FOUND));
        CustomizedError error = responseEntity.getBody();
        assertThat(error.getCode(), is(RECORD_NOT_FOUND));
        assertThat(error.getMessage(), is("Visitor not found"));
    }

    @Test
    public void shouldCreateVisitorSuccessfully() throws Exception {
        CreateVisitorRequest createVisitorRequest = new CreateVisitorRequest();
        createVisitorRequest.setRealName("小明");
        createVisitorRequest.setGender(MAIL);
        Date dob = new DateTime().withDate(1985, 10, 1).toDate();
        createVisitorRequest.setDob(dob);
        createVisitorRequest.setAge(2018 - 1985);
        createVisitorRequest.setMobile("13900002222");

        ResponseEntity<CreateVisitorResponse> response = restTemplate.postForEntity("/visitors", createVisitorRequest, CreateVisitorResponse.class);

        assertThat(response.getStatusCode(), is(CREATED));
        CreateVisitorResponse body = response.getBody();
        assertThat(body.getId(), is(greaterThan(0)));
    }

    private Visitor createVisitor(String name, String mobile) {
        visitorDAO.create(name, mobile);
        Optional<Visitor> visitor = visitorDAO.find(name, mobile);
        return visitor.get();
    }
}
