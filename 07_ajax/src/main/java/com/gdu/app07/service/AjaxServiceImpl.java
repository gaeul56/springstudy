package com.gdu.app07.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdu.app07.dao.AjaxDao;
import com.gdu.app07.dto.AjaxDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AjaxServiceImpl implements AjaxService {

	private final AjaxDao  dao1;
	
	
	
	@Override
	public List<AjaxDto> getDtoList() {
		return dao1.getDtoList();
	}
	@Override
	public AjaxDto getDto(String name) {
		// TODO Auto-generated method stub
		int no = 0;
		if(name.equals("뽀로로")) {
			no = 1;
		} else if (name.equals("루피")) {
			no = 2;
		} else if (name.equals("크롱")) {
			no = 3;
		}
		return dao1.getDto(no);
	}

}
