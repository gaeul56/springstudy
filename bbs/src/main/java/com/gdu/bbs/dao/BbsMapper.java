package com.gdu.bbs.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.bbs.dto.BbsDto;

@Mapper
public interface BbsMapper {


	public List<BbsDto> getBbsList(Map<String, Object> map);
	public int getBbsCount();
	//bbsMapper.xml에서 BbsDto로 반환하기 때문에 void 로 전달했어도 BbsDto 사용
	public BbsDto getBbs(int bbsNo);
	public int insertBbs(BbsDto bbs);
	public int updateBbs(BbsDto bbs);
	public int removeBbs(int bbsNo);
}
