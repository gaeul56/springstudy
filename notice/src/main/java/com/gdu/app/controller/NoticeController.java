package com.gdu.app.controller;

import org.springframework.stereotype.Controller;

import com.gdu.app.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class NoticeController {
	
	private final NoticeService noticeService;

	
}
