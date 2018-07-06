package com.zhiliao.api.zhiliaoapi.services;

import com.zhiliao.api.zhiliaoapi.dao.ConsultantDAO;
import com.zhiliao.api.zhiliaoapi.exceptions.LoginFailedException;
import com.zhiliao.api.zhiliaoapi.exceptions.RegisterFailedException;
import com.zhiliao.api.zhiliaoapi.models.Consultant;
import com.zhiliao.api.zhiliaoapi.utils.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.zhiliao.api.zhiliaoapi.utils.SecurityHelper.hash;

@Service
public class AuthService {

    @Autowired
    private ConsultantDAO consultantDAO;

    public String generateToken() {
        return SecurityHelper.generateUUID().replaceAll("-", "");
    }

    public Consultant login(String mobile, String passwordInPlaintext) {
        Optional<Consultant> consultant = consultantDAO.findOne(mobile, hash(passwordInPlaintext));

        if(consultant.isPresent()) {
            return consultant.get();
        } else {
            throw new LoginFailedException("Invalid credential.");
        }
    }

    public Consultant register(String mobile, String password) throws RegisterFailedException {
        Optional<Consultant> consultant = consultantDAO.findOne(mobile);
        if (consultant.isPresent()) {
            throw new RegisterFailedException("consultant already registered.");
        }

        String hashedPassword = hash(password);
        consultantDAO.create(mobile, hashedPassword);
        Optional<Consultant> consultantRetrievedFromDB = consultantDAO.findOne(mobile, hashedPassword);

        if (consultantRetrievedFromDB.isPresent()) {
            return consultantRetrievedFromDB.get();
        } else {
            throw new RegisterFailedException("Register failed.");
        }
    }
}
