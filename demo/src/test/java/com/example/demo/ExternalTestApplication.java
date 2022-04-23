package com.example.demo;

import com.example.demo.config.MyBatisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(excludeFilters={
        @ComponentScan.Filter(
                type=FilterType.ASSIGNABLE_TYPE,
                value=MyBatisConfig.class)
})
public class ExternalTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExternalTestApplication.class, args);
    }
}
