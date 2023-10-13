package com.gdu.app02.anno01;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class AppConfig {

	@Bean
	public Calculator calc() {
		return new Calculator();
	}
}
