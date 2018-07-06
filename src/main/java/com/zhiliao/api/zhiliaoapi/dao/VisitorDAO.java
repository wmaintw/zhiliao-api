package com.zhiliao.api.zhiliaoapi.dao;

import com.zhiliao.api.zhiliaoapi.httpObjects.visitor.CreateVisitorRequest;
import com.zhiliao.api.zhiliaoapi.models.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Transactional
@Repository
public class VisitorDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Visitor> findById(int id) {
        String findVisitor = "select * from visitors where id = ?";
        List<Visitor> visitors = jdbcTemplate.query(findVisitor,
                new Object[]{id}, findVisitorMapper());
        return isFindTargetUser(visitors) ? of(visitors.get(0)) : empty();
    }

    public List<Visitor> findByConsultantId(int consultantId) {
        String findVisitors = "select * from visitors where consultant_id = ?";
        List<Visitor> visitors = jdbcTemplate.query(findVisitors,
                new Object[]{consultantId}, findVisitorMapper());
        return visitors;
    }

    public Optional<Visitor> find(String name, String mobile) {
        String findVisitor = "select * from visitors where real_name like ? and mobile = ?";
        List<Visitor> visitors = jdbcTemplate.query(findVisitor,
                new Object[]{name, mobile}, findVisitorMapper());
        return isFindTargetUser(visitors) ? of(visitors.get(0)) : empty();
    }

    public void create(CreateVisitorRequest visitor) {
        String insertVisitor = "insert into visitors (real_name, gender, dob, age, mobile) values (?, ?, ?, ?, ?)";
        jdbcTemplate.execute(insertVisitor, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, visitor.getRealName());
                ps.setString(2, visitor.getGender());
                ps.setDate(3, new Date(visitor.getDob().getTime()));
                ps.setInt(4, visitor.getAge());
                ps.setString(5, visitor.getMobile());
                return ps.execute();
            }
        });
    }

    public void create(String name, String mobile, int consultantId) {
        String insertVisitor = "insert into visitors (real_name, mobile, consultant_id) values (?, ?, ?)";
        jdbcTemplate.execute(insertVisitor, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, name);
                ps.setString(2, mobile);
                ps.setInt(3, consultantId);
                return ps.execute();
            }
        });
    }

    private boolean isFindTargetUser(List<Visitor> visitors) {
        return visitors.size() == 1;
    }

    private RowMapper<Visitor> findVisitorMapper() {
        return new RowMapper<Visitor>() {
            @Override
            public Visitor mapRow(ResultSet rs, int rowNum) throws SQLException {
                Visitor visitor = new Visitor();
                visitor.setId(rs.getInt("id"));
                visitor.setRealName(rs.getString("real_name"));
                visitor.setMobile(rs.getString("mobile"));
                return visitor;
            }
        };
    }

    public void deleteAll() {
        jdbcTemplate.execute("truncate table visitors");
    }
}
