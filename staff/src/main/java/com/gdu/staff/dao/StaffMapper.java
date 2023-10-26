package com.gdu.staff.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.staff.dto.StaffDto;

@Mapper
public interface StaffMapper {
	//전체조회
	public List<StaffDto> getStaffList();
	
	//개별조회
    public StaffDto getStaff(String sno);
    
	//삽입 
    public int insertStaff(StaffDto staff);

}
