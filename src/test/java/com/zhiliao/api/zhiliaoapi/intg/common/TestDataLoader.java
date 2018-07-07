package com.zhiliao.api.zhiliaoapi.intg.common;

import com.zhiliao.api.zhiliaoapi.models.Consultant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestDataLoader extends ControllerTestBase {
    @Autowired
    private TestDataHelper dataHelper;

    @Test
    public void loadTestData() throws Exception {
        dataHelper.truncateAll();

        Consultant consultant1 = dataHelper.createConsultant("15900001111", "123");
        Consultant consultant2 = dataHelper.createConsultant("15900002222", "123");

        dataHelper.aVisitor("小张", "13511111111", consultant1.getId());
        dataHelper.aVisitor("小王", "13522222222", consultant1.getId());

        dataHelper.aVisitor("小明", "13533333333", consultant2.getId());
    }
}
