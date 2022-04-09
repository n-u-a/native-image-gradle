package com.example.demo.config;

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
@MapperScan("com.example.demo.mapper")
public class MyBatisConfig {

    // データソースのBean定義をします。
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .url("jdbc:mysql://localhost:3306/sampledb")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("sample_user")
                .password("P@ssw0rd")
                .build();
    }

    // org.mybatis.spring.SqlSessionFactoryBeanをBean定義します。
    // これによりSqlSessionFactoryBeanを利用してSqlSessionFactoryが生成されます。
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
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