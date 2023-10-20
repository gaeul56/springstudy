package com.gdu.myapp.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.myapp.dao.NoticeMapper;
import com.gdu.myapp.dto.NoticeDto;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
	private final NoticeMapper noticeDao;
	
	 @Override
	  public int addNotice(NoticeDto noticeDto) {
	    int addResult = noticeDao.insert(noticeDto);
	    return addResult;
	  }
	 @Override
	public int modifyNotice(NoticeDto noticeDto) {
		int modifyResult = noticeDao.update(noticeDto);
		return modifyResult;
	}
	 
	 @Override
	public int deleteNotice(int noticeNo) {
		 int deleteResult = noticeDao.delete(noticeNo);
		 return deleteResult;
	} 
	 @Override
	public List<NoticeDto> getNoticeList() {
		 return noticeDao.selectList();
	}
	 @Override
	public NoticeDto getNoticeByNo(int noticeNo) {
		return noticeDao.selectNoticeByNo(noticeNo);
	}
	 
}
