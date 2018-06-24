package com.zhiliao.api.zhiliaoapi.unit.services;

import com.zhiliao.api.zhiliaoapi.dao.VisitorDAO;
import com.zhiliao.api.zhiliaoapi.exceptions.CreateFailedException;
import com.zhiliao.api.zhiliaoapi.exceptions.RecordNotFoundException;
import com.zhiliao.api.zhiliaoapi.httpObjects.visitor.CreateVisitorRequest;
import com.zhiliao.api.zhiliaoapi.models.Visitor;
import com.zhiliao.api.zhiliaoapi.services.VisitorService;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class VisitorServiceTest {

    private VisitorService visitorService;
    private VisitorDAO visitorDAO;

    @Before
    public void setUp() throws Exception {
        visitorService = new VisitorService();
        visitorDAO = mock(VisitorDAO.class);

        visitorService.setVisitorDAO(visitorDAO);
    }

    @Test
    public void shouldReturnSingleVisitorIfFoundInDb() throws Exception {
        int visitorId = 100;
        Visitor mockedVisitor = new Visitor();
        mockedVisitor.setId(visitorId);

        when(visitorDAO.findById(visitorId)).thenReturn(of(mockedVisitor));
        Visitor visitor = visitorService.findVisitor(visitorId);

        assertThat(visitor.getId(), is(mockedVisitor.getId()));
        verify(visitorDAO).findById(visitorId);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowExceptionIfNotFoundInDb() throws Exception {
        int nonExistVisitorId = 200;
        when(visitorDAO.findById(nonExistVisitorId)).thenReturn(empty());

        visitorService.findVisitor(nonExistVisitorId);

        verify(visitorDAO).findById(nonExistVisitorId);
    }

    @Test
    public void shouldReturnVisitorIdAfterCreated() throws Exception {
        CreateVisitorRequest visitorRequest = new CreateVisitorRequest();
        Optional<Visitor> mockedVisitor = of(getVisitor());

        doNothing().when(visitorDAO).create(visitorRequest);
        when(visitorDAO.find(visitorRequest.getRealName(), visitorRequest.getMobile())).thenReturn(mockedVisitor);

        int id = visitorService.createVisitor(visitorRequest);

        assertThat(id, is(mockedVisitor.get().getId()));
    }

    @Test(expected = CreateFailedException.class)
    public void shouldThrowExceptionAfterCreateFailed() throws Exception {
        CreateVisitorRequest visitorRequest = new CreateVisitorRequest();

        doNothing().when(visitorDAO).create(visitorRequest);
        when(visitorDAO.find(visitorRequest.getRealName(), visitorRequest.getMobile())).thenReturn(empty());

        visitorService.createVisitor(visitorRequest);
    }

    private Visitor getVisitor() {
        Visitor visitor = new Visitor();
        visitor.setId(100);
        visitor.setRealName("小明");
        visitor.setMobile("13900001111");
        return visitor;
    }
}
