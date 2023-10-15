package com.gdu.app13.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration //Spring의 설정파일임
public class AppConfig {
	
	@Bean //스프링 컨테이너에 빈으로 등록할 메소드를 표시
		  //MultipartResolver를 Bean으로 등록하는 메소드
	public MultipartResolver multipartResolver() { // 메소드는 MultipartResolver를 구성하고 반환하는 메소드
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(); //CommonsMultipartResolver 객체를 생성
		commonsMultipartResolver.setDefaultEncoding("UTF-8");	//UTF-8로 설정
		commonsMultipartResolver.setMaxUploadSize(1024*1024*100); //전체 첨부파일의 크기 최대크기 100Mb
		commonsMultipartResolver.setMaxUploadSizePerFile(1024*1024*10); //개별 첨부파일의 최대 크기 10MB
		
		return commonsMultipartResolver; //설정한 CommonsMultipartResolver를 반환
	}

}
