package com.gdu.myapp.service;

import java.util.List;

import com.gdu.myapp.dto.NoticeDto;

public interface NoticeService {
	 public int addNotice(NoticeDto noticeDto);
	 public int modifyNotice(NoticeDto noticeDto);
	 public int deleteNotice(int noticeNo);
	 public List<NoticeDto> getNoticeList();
	 public NoticeDto getNoticeByNo(int noticeNo);
}
