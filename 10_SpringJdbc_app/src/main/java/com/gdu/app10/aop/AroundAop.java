package com.gdu.app10.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AroundAop {

	//포인트컷: 언제 동작하는가?
	@Pointcut("execution(* com.gdu.app10.controller.*Controller.*(..))")
	public void setPointCut() { }
	//어드바이스: 무슨 동작을하는가?
	@Around("setPointCut()")
	public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		/*
		 * Around 어드바이스
		 * 1. 반환타입: Object
		 * 2. 메소드명: 마음대로
		 * 3. 매개변수: ProceedingJoinPoint
		 */
	
		log.info(null);								// 포인트컷 실행 이후에 실행 (@After 이전에 동작)
		
		return obj;
	}
}
