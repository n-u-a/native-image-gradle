package com.example.demo.dao;

import com.example.demo.domain.UserDomain;
import com.example.demo.mapper.SampleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SampleDao {

    @Autowired
    private SampleMapper mapper;

    public UserDomain getUser(String userId) {
        return mapper.getUser(userId);
    }

}
