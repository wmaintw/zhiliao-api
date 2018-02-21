package com.zhiliao.api.zhiliaoapi.controllers;

import com.zhiliao.api.zhiliaoapi.common.ControllerTestBase;
import com.zhiliao.api.zhiliaoapi.httpObjects.InfoResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class InfoControllerTest extends ControllerTestBase {
    @Test
    public void shouldReturnStatusMessage() throws Exception {
        InfoResponse info = restTemplate.getForObject("/info", InfoResponse.class);
        assertThat(info, not(nullValue()));
        assertThat(info.getStatus(), is("up!"));
    }
}
