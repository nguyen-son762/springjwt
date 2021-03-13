package com.java.springsecuirty.controller;

import com.java.springsecuirty.Constants;
import com.java.springsecuirty.model.User;
import com.java.springsecuirty.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")

public class UserController {
    @Autowired
    UserService userService;
    @PostMapping(value = "/register")
    public ResponseEntity<Map<String, String>> register(@RequestParam String firstname,
                                                        @RequestParam String lastname,
                                                        @RequestParam String email,
                                                        @RequestParam String password) {
        User user=userService.registerUser(firstname,lastname,email,password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }
    @GetMapping("/register")
    public String regist1er(){
        return "Hello";
    }

    private Map<String, String> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userid", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstname", user.getFirstName())
                .claim("lastname", user.getLastName())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
