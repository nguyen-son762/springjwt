package com.java.springsecuirty.service;

import com.java.springsecuirty.exception.EtAuthException;
import com.java.springsecuirty.model.User;

public interface UserService {
    User validateUser(String email,String password) throws EtAuthException;
    User registerUser(String firstName,String lastName,String email,String password) throws EtAuthException;
}
