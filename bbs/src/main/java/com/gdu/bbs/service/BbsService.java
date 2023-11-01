package com.gdu.bbs.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.gdu.bbs.dto.BbsDto;


public interface BbsService {	
	public void loadBbsList(HttpServletRequest request, Model model); 
	/*
	 * HttpServletRequest를 사용하는 이유
	 * 1. getParameter로 페이지 받을라고
	 * 2. contextPath 사용하려고
	 */
	public BbsDto getBbs(int bbsNo);
	public int addBbs(BbsDto bbs);
	public int modifyBbs(BbsDto bbs);
	public int deleteBbs(int bbsNo);
}
