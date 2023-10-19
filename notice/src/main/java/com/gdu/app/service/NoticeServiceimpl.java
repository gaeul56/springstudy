package com.gdu.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.app.dao.NoticeMapper;
import com.gdu.app.dto.NoticeDto;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class NoticeServiceimpl implements NoticeService {

  private final NoticeMapper noticeMapper;
  
  @Override
  public int modifyNotice(NoticeDto noticeDto) {
    return noticeMapper.modifyNotice(noticeDto);
  }
  
  @Override
  public NoticeDto getNotice(int noticeNo) {
    return noticeMapper.getNotice(noticeNo);
  }
  
  @Override
  public int addNotice(NoticeDto noticeDto) {
    return noticeMapper.addNotice(noticeDto);
  }
  
  @Override
  public List<NoticeDto> getNoticeList() {
    return noticeMapper.getNoticeList();
  }
  
  @Override
	public int deleteNotice(int noticeNo) {
		return noticeMapper.deleteNotice(noticeNo);
	}
  
}
