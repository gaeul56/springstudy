package com.gdu.app10.config;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

//Spring Framework를 사용하여 AOP를 이용한 트랜잭션 관리를 설정하는 Java 구성 클래스


@EnableAspectJAutoProxy // AspectJ AOP 프록시 지원을 활성화합니다.
@Configuration	// 스프링 설정 클래스임을 나타
public class AppConfig {

  // dataSource()는 DataSource 빈을 생성하는 메서드
  // DataSource : CP(Connection Pool)을 처리하는 javax.sql.DataSource 인터페이스
  // DriverManagerDataSource를 사용하여 데이터베이스 연결정보를 설정하고 데이터 소스를 반환
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();  // DriverManagerDataSource : CP(Connection Pool)을 처리하는 스프링 클래스
    dataSource.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
    dataSource.setUrl("jdbc:log4jdbc:oracle:thin:@localhost:1521:xe");
    dataSource.setUsername("GD");
    dataSource.setPassword("1111");
    return dataSource;
  }
  // jdbcTemplate()는 JdbcTemplate 빈을 생성하는 메서드
  // JdbcTemplate : Jdbc를 처리하는 스프링 클래스(Connection, PreparedStatement, ResultSet 처리 담당)
  // dataSource() 메서드를 호출하여 데이터베이스 연결을 처리하는 JdbcTemplate 객체를 반환
  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }
  // transactionManager()는 TranscationManager 빈을 생성하는 메서드
  // TransactionManager : 트랜잭션을 처리하는 스프링 인터페이스
  // dataSource() 메서드를 호출하아여 데이터베이스 연결을 처리하는 DataSourceTransactionManager 객체를 반환
  @Bean
  public TransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }

  /* 지금부터 AOP를 이용한 트랜잭션 처리를 위해 필요한 Bean */
  
  // TransactionInterceptor : 트랜잭션 처리를 위해 언제 rollback 할 것인지 정의하는 스프링 클래스
  @Bean
  public TransactionInterceptor transactionInterceptor() {
    
    // 규칙
    RuleBasedTransactionAttribute ruleBasedTransactionAttribute = new RuleBasedTransactionAttribute();
    ruleBasedTransactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
    
    MatchAlwaysTransactionAttributeSource matchAlwaysTransactionAttributeSource = new MatchAlwaysTransactionAttributeSource();
    matchAlwaysTransactionAttributeSource.setTransactionAttribute(ruleBasedTransactionAttribute);
    
    // 반환
    return new TransactionInterceptor(transactionManager(), matchAlwaysTransactionAttributeSource);
    
  }
  
  // Advisor : AOP에서 언제 트랜잭션을 적용할지 결정하기 위한 Advisor를 설정합니다.
  @Bean
  public Advisor advisor() {
    
    // AspextJExpressionPointcut을 사용하여 어떤 메서드 호출에 트랜잭션을 적용할지를 정의합니다.
    AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
//com.gdu.app10.service 패키지의 모든 메서드 호출에 트랜잭션을 적용	
    aspectJExpressionPointcut.setExpression("execution(* com.gdu.app10.service.*Impl.*(..))");
    
    // 어드바이스 반환
    return new DefaultPointcutAdvisor(aspectJExpressionPointcut, transactionInterceptor());
    
  }
  
}