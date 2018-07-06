package com.zhiliao.api.zhiliaoapi.utils;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SecurityHelperTest {

    @Test
    public void shouldExtractTokenFromHeader() throws Exception {
        String targetToken= "test-token";
        String token = SecurityHelper.extractToken("Bearer " + targetToken);
        assertThat(token, is(targetToken));
    }
}