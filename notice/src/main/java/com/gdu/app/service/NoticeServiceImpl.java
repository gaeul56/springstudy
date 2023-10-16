package com.gdu.app.service;

import org.springframework.stereotype.Service;

import com.gdu.app.dao.NoticeMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //noticeMapper 불러오기 위해서 사
@Service
public class NoticeServiceImpl implements NoticeService {

	private final NoticeMapper noticeMapper;
	
}
