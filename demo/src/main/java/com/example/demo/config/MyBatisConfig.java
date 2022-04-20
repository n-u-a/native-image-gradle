package com.example.demo.config;

import com.example.demo.mapper.SampleMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
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

        return DataSourceBuilder
                .create()
                .url("jdbc:mysql://kutnpvrhom7lki7u.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/uwb34nd2h7knjsoc?serverTimezone=JST")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("bb7vvwyqjw2q9ztb")
                .password("artu4fp7wsnfsk5c")
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