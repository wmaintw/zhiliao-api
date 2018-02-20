package com.zhiliao.api.zhiliaoapi.services;

import com.zhiliao.api.zhiliaoapi.dao.UserDAO;
import com.zhiliao.api.zhiliaoapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static com.zhiliao.api.zhiliaoapi.utils.SecurityHelper.hash;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public Optional<User> findUser(String mobile, String passwordInPlaintext) {
        try {
            return userDAO.findUser(mobile, hash(passwordInPlaintext));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return Optional.ofNullable(null);
        }
    }
}
