package com.example.demo.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@TestConfiguration
@EnableTransactionManagement
@MapperScan(value="com.example.demo.mapper", sqlSessionFactoryRef="testSqlSessionFactory")
public class TestMyBatisConfig {

    // データソースのBean定義をします。
    @Bean
    public DataSource testDataSource() {

        return DataSourceBuilder
                .create()
                .url("jdbc:mysql://localhost:3306/sampledb")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("sample_user")
                .password("P@ssw0rd")
                .build();
    }

    @Bean
    public SqlSessionFactoryBean testSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        // データソースを設定する。MyBatisの処理の中でSQLを発行すると、
        // ここで指定したデータソースからコネクションが取得されます。
        sessionFactoryBean.setDataSource(testDataSource());
        // MyBatis設定ファイルを指定します。
        // 今回はresources直下に設定ファイルを配置します。
        sessionFactoryBean.setConfigLocation(new ClassPathResource("/mybatis-config.xml"));
        return sessionFactoryBean;
    }

    // トランザクションマネージャーのBeanを定義します。
    @Bean
    public PlatformTransactionManager testTransactionManager() {
        return new DataSourceTransactionManager(testDataSource());
    }
}
