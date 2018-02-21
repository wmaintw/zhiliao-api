package com.zhiliao.api.zhiliaoapi.dao;

import com.zhiliao.api.zhiliaoapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
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

    public Optional<User> findOne(String mobile, String hashedPassword) {
        List<User> users = jdbcTemplate.query("select * from users where mobile = ? and password = ?",
                new Object[]{mobile, hashedPassword}, getUserMapper());
        return isFindTargetUser(users) ? of(users.get(0)) : ofNullable(null);
    }

    public Optional<User> findOne(String mobile) {
        List<User> users = jdbcTemplate.query("select * from users where mobile = ?",
                new Object[]{mobile}, getUserMapper());
        return isFindTargetUser(users) ? of(users.get(0)) : ofNullable(null);
    }

    public void create(String mobile, String hashedPassword) {
        String insertUser = "insert into users (mobile, password) values (?, ?)";
        jdbcTemplate.execute(insertUser, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, mobile);
                ps.setString(2, hashedPassword);
                return ps.execute();
            }
        });
    }

    public void deleteAll() {
        jdbcTemplate.execute("truncate table users");
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
