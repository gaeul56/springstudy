package com.gdu.app13.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app13.service.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FileController {
	
	private final FileService fileService;

	@RequestMapping(value="/upload.do", method=RequestMethod.POST)
	public String upload(MultipartHttpServletRequest multipartRequest) {
		fileService.upload(multipartRequest);
		return "";
	}
}
