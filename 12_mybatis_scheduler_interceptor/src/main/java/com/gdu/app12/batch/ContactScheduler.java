package com.gdu.app12.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.app12.service.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class ContactScheduler {
	
	private final ContactService contactService;
	
	@Scheduled(cron="0/60 * * * * ?") //1분마다 가장 예전에 등록된 연락처를 삭제하는 스케줄
	public void doSomethig() {
		contactService.deleteOld();
		log.info("doSomething()");
	}
}