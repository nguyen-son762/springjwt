package com.java.springsecuirty.repository;

import com.java.springsecuirty.exception.EtAuthException;
import com.java.springsecuirty.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
@Repository
public class UserRepositoryImpl implements UserRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;
    private static final String SQL_CREATE = "INSERT INTO ET_USERS(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM ET_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM ET_USERS WHERE EMAIL = ?";
    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException {
        KeyHolder keyHolder=new GeneratedKeyHolder();
        try{
            jdbcTemplate.update(con -> {
                PreparedStatement ps=con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,firstName);
                ps.setString(2,lastName);
                ps.setString(3,email);
                ps.setString(4,password);
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        }
        catch (Exception e){
            throw new EtAuthException("Invalid details.Failed to create account");
        }
    }

    @Override
    public User findUser(String email, String password) throws EtAuthException {
        return null;
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL,new Object[]{email},Integer.class);
    }

    @Override
    public User findById(Integer id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,new Object[]{id},userRowMapper);
    }
    private RowMapper<User> userRowMapper=((rs, rowNum) -> {
        return new User(rs.getInt("USER_ID"),
                rs.getString("FIRTST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"));
    });
}
