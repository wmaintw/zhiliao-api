package com.zhiliao.api.zhiliaoapi.dao;

import com.zhiliao.api.zhiliaoapi.models.Consultant;
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
public class ConsultantDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ConsultantDAO() {
    }

    public Optional<Consultant> findOne(String mobile, String hashedPassword) {
        List<Consultant> consultants = jdbcTemplate.query("select * from consultants where mobile = ? and password = ?",
                new Object[]{mobile, hashedPassword}, getConsultantMapper());
        return isFindTargetConsultant(consultants) ? of(consultants.get(0)) : ofNullable(null);
    }

    public Optional<Consultant> findOne(String mobile) {
        List<Consultant> consultants = jdbcTemplate.query("select * from consultants where mobile = ?",
                new Object[]{mobile}, getConsultantMapper());
        return isFindTargetConsultant(consultants) ? of(consultants.get(0)) : ofNullable(null);
    }

    public void create(String mobile, String hashedPassword) {
        String insertConsultant = "insert into consultants (mobile, password) values (?, ?)";
        jdbcTemplate.execute(insertConsultant, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, mobile);
                ps.setString(2, hashedPassword);
                return ps.execute();
            }
        });
    }

    public void deleteAll() {
        jdbcTemplate.execute("truncate table consultants");
    }

    private RowMapper<Consultant> getConsultantMapper() {
        return new RowMapper<Consultant>() {
            @Override
            public Consultant mapRow(ResultSet rs, int rowNum) throws SQLException {
                Consultant consultant = new Consultant();
                consultant.setId(rs.getInt("id"));
                consultant.setMobile(rs.getString("mobile"));
                consultant.setPassword(rs.getString("password"));
                consultant.setName(rs.getString("name"));
                return consultant;
            }
        };
    }

    private boolean isFindTargetConsultant(List<Consultant> consultants) {
        return consultants.size() == 1;
    }
}
