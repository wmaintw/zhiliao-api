package com.zhiliao.api.zhiliaoapi.unit.services;

import com.zhiliao.api.zhiliaoapi.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

}
