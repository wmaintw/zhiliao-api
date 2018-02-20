package com.zhiliao.api.zhiliaoapi.dao;

import com.zhiliao.api.zhiliaoapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Transactional
@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDAO() {
    }

    public Optional<User> findUser(String mobile, String encryptedPassword) {
        List<User> users = jdbcTemplate.query("select * from users where mobile = ? and password = ?",
                new Object[]{mobile, encryptedPassword}, getUserMapper());
        return isFindTargetUser(users) ? of(users.get(0)) : ofNullable(null);
    }

    private RowMapper<User> getUserMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setMobile(rs.getString("mobile"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                return user;
            }
        };
    }

    private boolean isFindTargetUser(List<User> users) {
        return users.size() == 1;
    }
}
