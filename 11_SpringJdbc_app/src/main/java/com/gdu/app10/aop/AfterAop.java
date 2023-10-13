package com.gdu.app10.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AfterAop {

  // 포인트컷 : 언제 동작하는가?
  @Pointcut("execution(* com.gdu.app10.controller.*Controller.*(..))")
  public void setPointCut() { }
  
  // 어드바이스 : 무슨 동작을 하는가?
  @After("setPointCut()")
  public void afterAdvice(JoinPoint joinPoint) {
    
    /*
     * After 어드바이스
     * 1. 반환타입 : void
     * 2. 메소드명 : 마음대로
     * 3. 매개변수 : JoinPoint
     */
    
    // 로그 찍기
    log.info("==================================================================");
    Object obj = 
	log.info("{}", new SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS").format(new Date()));	// 포인트컷 실행 이전에 실행 (@Before 이전에 동작)"

  }
  
}