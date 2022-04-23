package com.example.demo.config;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
// @org.springframework.transaction.annotation.EnableTransactionManagementを付与し、
// アノテーション駆動(@Transactional)のトランザクション制御を有効にします。
@EnableTransactionManagement
// @org.mybatis.spring.annotation.MapperScanを付与し、Mapperインターフェースのスキャンを有効にします。
@MapperScan(value="com.example.demo.mapper", sqlSessionFactoryRef="sqlSessionFactory")
public class MyBatisConfig {

    // データソースのBean定義をします。
    @Bean
    public DataSource dataSource() {

        String DB_URL = StringUtils.isEmpty(System.getenv("DB_URL")) ? "jdbc:mysql://alocalhost:3306/" : System.getenv("DB_URL");
        String DB_USER_NAME = StringUtils.isEmpty(System.getenv("DB_USER_NAME")) ? "sample_user" : System.getenv("DB_USER_NAME");
        String DB_PASSWORD = StringUtils.isEmpty(System.getenv("DB_PASSWORD")) ? "P@ssw0rd" : System.getenv("DB_PASSWORD");

        return DataSourceBuilder
                .create()
                .url(DB_URL)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username(DB_USER_NAME)
                .password(DB_PASSWORD)
                .build();
    }

    // org.mybatis.spring.SqlSessionFactoryBeanをBean定義します。
    // これによりSqlSessionFactoryBeanを利用してSqlSessionFactoryが生成されます。
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        // データソースを設定する。MyBatisの処理の中でSQLを発行すると、
        // ここで指定したデータソースからコネクションが取得されます。
        sessionFactoryBean.setDataSource(dataSource());
        // MyBatis設定ファイルを指定します。
        // 今回はresources直下に設定ファイルを配置します。
        sessionFactoryBean.setConfigLocation(new ClassPathResource("/mybatis-config.xml"));
        return sessionFactoryBean;
    }

    // トランザクションマネージャーのBeanを定義します。
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}