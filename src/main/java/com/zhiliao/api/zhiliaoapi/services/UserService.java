package com.zhiliao.api.zhiliaoapi.services;

import com.zhiliao.api.zhiliaoapi.dao.UserDAO;
import com.zhiliao.api.zhiliaoapi.exceptions.RecordNotFoundException;
import com.zhiliao.api.zhiliaoapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User find(String mobile) {
        Optional<User> consultant = userDAO.findOne(mobile);
        if (consultant.isPresent()) {
            return consultant.get();
        } else {
            throw new RecordNotFoundException("Consultant not found");
        }
    }
}
