package com.zhiliao.api.zhiliaoapi.services;

import com.zhiliao.api.zhiliaoapi.dao.VisitorDAO;
import com.zhiliao.api.zhiliaoapi.exceptions.CreateFailedException;
import com.zhiliao.api.zhiliaoapi.exceptions.RecordNotFoundException;
import com.zhiliao.api.zhiliaoapi.httpObjects.visitor.CreateVisitorRequest;
import com.zhiliao.api.zhiliaoapi.models.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitorService {

    @Autowired
    VisitorDAO visitorDAO;

    public Visitor findVisitor(int id) {
        Optional<Visitor> visitor = visitorDAO.findById(id);
        if (visitor.isPresent()) {
            return visitor.get();
        } else {
            throw new RecordNotFoundException("Visitor not found");
        }
    }

    public List<Visitor> findMyVisitors(int consultantId) {
        List<Visitor> visitors = visitorDAO.findByConsultantId(consultantId);
        return visitors;
    }

    public int createVisitor(CreateVisitorRequest visitor) {
        visitorDAO.create(visitor);
        Optional<Visitor> createdVisitor = visitorDAO.find(visitor.getRealName(), visitor.getMobile());
        if (createdVisitor.isPresent()) {
            return createdVisitor.get().getId();
        } else {
            throw new CreateFailedException("Failed to create visitor");
        }
    }

    public VisitorDAO getVisitorDAO() {
        return visitorDAO;
    }

    public void setVisitorDAO(VisitorDAO visitorDAO) {
        this.visitorDAO = visitorDAO;
    }
}
