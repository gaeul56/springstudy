package com.gdu.myapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myapp.dto.NoticeDto;

@Mapper
public interface NoticeMapper {

	public int insert(NoticeDto noticeDto);
	public int update(NoticeDto noitceDto);
	public int delete(int noticeNo);
	public List<NoticeDto> selectList();
	public NoticeDto selectNoticeByNo(int noticeNo);
}
