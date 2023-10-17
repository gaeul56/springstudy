package com.gdu.app.service;

import java.util.List;

import com.gdu.app.dto.NoticeDto;

public interface NoticeService {
	int modifyNotice(NoticeDto noticeDto);
	NoticeDto getNotice(int noticeNo);
	//addNotice(HttpServletRequest request);
	//addNotice(int gubun, String title, String content)
	int addNotice(NoticeDto noticeDto);
	List<NoticeDto> getNoticeList();
  
}
