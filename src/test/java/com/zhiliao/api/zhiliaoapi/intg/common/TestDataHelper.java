package com.zhiliao.api.zhiliaoapi.intg.common;

import com.zhiliao.api.zhiliaoapi.dao.ConsultantDAO;
import com.zhiliao.api.zhiliaoapi.dao.VisitorDAO;
import com.zhiliao.api.zhiliaoapi.models.Consultant;
import com.zhiliao.api.zhiliaoapi.models.Visitor;
import com.zhiliao.api.zhiliaoapi.utils.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TestDataHelper {

    private final int DEFAULT_CONSULTANT_ID = 100;

    @Autowired
    private ConsultantDAO consultantDAO;

    @Autowired
    private VisitorDAO visitorDAO;

    public Consultant createConsultant(String mobile, String originalPassword) {
        consultantDAO.create(mobile, SecurityHelper.hash(originalPassword));
        return consultantDAO.findOne(mobile).get();
    }

    public Visitor aVisitor(String name, String mobile) {
        return aVisitor(name, mobile, DEFAULT_CONSULTANT_ID);
    }

    public Visitor aVisitor(String name, String mobile, int consultantId) {
        visitorDAO.create(name, mobile, consultantId);
        Optional<Visitor> visitor = visitorDAO.find(name, mobile);
        return visitor.get();
    }

    public void truncateAll() {
        visitorDAO.deleteAll();
        consultantDAO.deleteAll();
    }
}
