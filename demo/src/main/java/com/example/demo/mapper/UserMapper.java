package com.example.demo.mapper;

import com.example.demo.domain.UserDomain;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    UserDomain getUser(@Param("userId")String userId);

    void insertUser(@Param("user")UserDomain userDomain);

}
