package com.zhiliao.api.zhiliaoapi.services;

import com.zhiliao.api.zhiliaoapi.dao.UserDAO;
import com.zhiliao.api.zhiliaoapi.models.User;
import com.zhiliao.api.zhiliaoapi.utils.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static com.zhiliao.api.zhiliaoapi.utils.SecurityHelper.hash;

@Service
public class AuthService {

    @Autowired
    private UserDAO userDAO;

    public String generateToken(User actualUser) {
        return SecurityHelper.generateUUID().replaceAll("-", "");
    }

    public Optional<User> login(String mobile, String passwordInPlaintext) {
        String encryptedPassword = null;
        try {
            encryptedPassword = hash(passwordInPlaintext);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return Optional.ofNullable(null);
        }
        return userDAO.findUser(mobile, encryptedPassword);
    }
}
