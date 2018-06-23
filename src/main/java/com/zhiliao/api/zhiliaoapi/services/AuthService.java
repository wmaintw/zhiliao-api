package com.zhiliao.api.zhiliaoapi.services;

import com.zhiliao.api.zhiliaoapi.dao.UserDAO;
import com.zhiliao.api.zhiliaoapi.exceptions.LoginFailedException;
import com.zhiliao.api.zhiliaoapi.exceptions.RegisterFailedException;
import com.zhiliao.api.zhiliaoapi.models.User;
import com.zhiliao.api.zhiliaoapi.utils.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.zhiliao.api.zhiliaoapi.utils.SecurityHelper.hash;

@Service
public class AuthService {

    @Autowired
    private UserDAO userDAO;

    public String generateToken() {
        return SecurityHelper.generateUUID().replaceAll("-", "");
    }

    public User login(String mobile, String passwordInPlaintext) {
        Optional<User> user = userDAO.findOne(mobile, hash(passwordInPlaintext));

        if(user.isPresent()) {
            return user.get();
        } else {
            throw new LoginFailedException("Invalid credential.");
        }
    }

    public User register(String mobile, String password) throws RegisterFailedException {
        Optional<User> user = userDAO.findOne(mobile);
        if (user.isPresent()) {
            throw new RegisterFailedException("user already registered.");
        }

        String hashedPassword = hash(password);
        userDAO.create(mobile, hashedPassword);
        Optional<User> userRetrievedFromDB = userDAO.findOne(mobile, hashedPassword);

        if (userRetrievedFromDB.isPresent()) {
            return userRetrievedFromDB.get();
        } else {
            throw new RegisterFailedException("Register failed.");
        }
    }
}
