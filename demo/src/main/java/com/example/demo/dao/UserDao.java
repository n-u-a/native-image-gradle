package com.example.demo.dao;

import com.example.demo.domain.UserDomain;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    private UserMapper mapper;

    public UserDomain getUser(String userId) {
        return mapper.getUser(userId);
    }

    public void insertUser(UserDomain user) {
        mapper.insertUser(user);
    }
}
