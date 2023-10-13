package com.gdu.app10.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j //log 객체를 생성
@Aspect	//이 클래스가 Aspect 클래스임을 나타냄. Aspect클래스는 AOP에서 어ㄸㄴ 메서드를 언제 실행할지를 정의합ㄴ디ㅏ.
@Component	//이 클래스가 Spring 컴포넌트로 스캔되도록 지시합니다. 
public class AfterAop {

  // 포인트컷 : 언제 동작하는가?
  @Pointcut("execution(* com.gdu.app10.controller.*Controller.*(..))") //패키지 내의 모든 controller클래스의 모든 메서드 호출을 포인트컷으로 지정
  public void setPointCut() { }
  
  // 어드바이스 : 무슨 동작을 하는가?
  @After("setPointCut()") // 메서드 호출 후에 실행될 어드바이스를 정의
  public void afterAdvice(JoinPoint joinPoint) { //실제로 실행되는 어드바이스 메서드
    
    /*
     * After 어드바이스
     * 1. 반환타입 : void
     * 2. 메소드명 : 마음대로
     * 3. 매개변수 : JoinPoint
     */
    
    // 로그 찍기
    log.info("==================================================================");
    
  }
  
}