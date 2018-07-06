package com.zhiliao.api.zhiliaoapi.services;

import com.zhiliao.api.zhiliaoapi.dao.ConsultantDAO;
import com.zhiliao.api.zhiliaoapi.exceptions.RecordNotFoundException;
import com.zhiliao.api.zhiliaoapi.models.Consultant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultantService {
    @Autowired
    private ConsultantDAO consultantDAO;

    public Consultant find(String mobile) {
        Optional<Consultant> consultant = consultantDAO.findOne(mobile);
        if (consultant.isPresent()) {
            return consultant.get();
        } else {
            throw new RecordNotFoundException("Consultant not found");
        }
    }
}
