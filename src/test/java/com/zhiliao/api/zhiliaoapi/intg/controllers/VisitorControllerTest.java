package com.zhiliao.api.zhiliaoapi.intg.controllers;


import com.zhiliao.api.zhiliaoapi.controllers.VisitorController;
import com.zhiliao.api.zhiliaoapi.dao.UserDAO;
import com.zhiliao.api.zhiliaoapi.dao.VisitorDAO;
import com.zhiliao.api.zhiliaoapi.httpObjects.CustomizedError;
import com.zhiliao.api.zhiliaoapi.httpObjects.visitor.CreateVisitorRequest;
import com.zhiliao.api.zhiliaoapi.httpObjects.visitor.CreateVisitorResponse;
import com.zhiliao.api.zhiliaoapi.intg.common.ControllerTestBase;
import com.zhiliao.api.zhiliaoapi.models.User;
import com.zhiliao.api.zhiliaoapi.models.Visitor;
import com.zhiliao.api.zhiliaoapi.utils.RedisHelper;
import com.zhiliao.api.zhiliaoapi.utils.SecurityHelper;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.zhiliao.api.zhiliaoapi.utils.CustomizedErrorCode.RECORD_NOT_FOUND;
import static com.zhiliao.api.zhiliaoapi.utils.Gender.MAIL;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.*;

public class VisitorControllerTest extends ControllerTestBase {

    private VisitorController controller;

    @Autowired
    private VisitorDAO visitorDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SecurityHelper securityHelper;

    @Autowired
    private RedisHelper redisHelper;

    @Before
    public void setUp() throws Exception {
        visitorDAO.deleteAll();
        userDAO.deleteAll();

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

    @Test
    public void shouldReturnAllMyVisitors() throws Exception {
        String mobileOfConsultantA = "13500000001";
        User consultantA = createConsultant(mobileOfConsultantA);
        User consultantB = createConsultant("13500000002");

        Visitor visitor1 = createVisitor("小明", "13900001111", consultantA.getId());
        Visitor visitor2 = createVisitor("小张", "13900002222", consultantA.getId());
        createVisitor("小王", "13900002222", consultantB.getId());

        String tokenOfConsultantA = "test-session-token";
        redisHelper.deleteKey(tokenOfConsultantA);
        securityHelper.saveLoginStatus(mobileOfConsultantA, tokenOfConsultantA);

        RequestEntity<Void> request = RequestEntity.get(new URI("/visitors"))
                .header("Authorization", "Bearer " + tokenOfConsultantA).build();
        ResponseEntity<List> response = restTemplate.exchange(request, List.class);

        assertThat(response.getStatusCode(), is(OK));
        List<HashMap> visitors = response.getBody();
        assertThat(visitors.size(), is(2));
        List<Integer> visitorIds = visitors.stream().map(visitor -> (Integer) visitor.get("id")).collect(toList());
        assertThat(visitorIds, hasItems(visitor1.getId(), visitor2.getId()));
    }

    private User createConsultant(String consultMobile) {
        userDAO.create(consultMobile, "lsjdfl");
        return userDAO.findOne(consultMobile).get();
    }

    private Visitor createVisitor(String name, String mobile) {
        return createVisitor(name, mobile, 100);
    }

    private Visitor createVisitor(String name, String mobile, int consultantId) {
        visitorDAO.create(name, mobile, consultantId);
        Optional<Visitor> visitor = visitorDAO.find(name, mobile);
        return visitor.get();
    }
}
