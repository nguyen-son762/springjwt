package com.java.springsecuirty.repository;

import com.java.springsecuirty.exception.EtAuthException;
import com.java.springsecuirty.model.User;

public interface UserRepository {
    Integer create(String firstName,String lastName,String email,String password) throws EtAuthException;
    User findUser(String email, String password) throws EtAuthException;
    Integer getCountByEmail(String email);
    User findById(Integer id);
}
