package com.example.demo.dao;

import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

public class DaoTestBase {

    protected DataSource dataSource() {

        return DataSourceBuilder
                .create()
                .url("jdbc:mysql://localhost:3306/sampledb")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("sample_user")
                .password("P@ssw0rd")
                .build();
    }
}
