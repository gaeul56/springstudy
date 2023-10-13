package com.gdu.app11.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;

@EnableAspectJAutoProxy
@Configuration
public class AppConfig {

  // DataSource : CP(Connection Pool)을 처리하는 javax.sql.DataSource 인터페이스
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();  // DriverManagerDataSource : CP(Connection Pool)을 처리하는 스프링 클래스
    dataSource.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
    dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@localhost:1521:xe");
    dataSource.setUsername("GD");
    dataSource.setPassword("1111");
    return dataSource;
  }
  
  // JdbcTemplate : Jdbc를 처리하는 스프링 클래스(Connection, PreparedStatement, ResultSet 처리 담당)
  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }

  // TransactionManager : 트랜잭션을 처리하는 스프링 인터페이스
  @Bean
  public TransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }
  
}