package com.gdu.bbs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.bbs.dto.BbsDto;
import com.gdu.bbs.service.BbsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BbsController {
	
	private final BbsService bbsService;
	
	// select는 model에 저장한 뒤 forward한다.
	@GetMapping("/list.do")
	public String list(HttpServletRequest request, Model model) {
	    // "/list.do" 요청을 처리하는 메서드입니다.
	    // bbsService를 사용하여 게시물 목록을 로드하고 모델에 추가합니다.
	    bbsService.loadBbsList(request, model);
	    // "bbs/list" 뷰를 반환하여 화면에 게시물 목록을 표시합니다.
	    return "bbs/list";
	}
	
	// ?bbsNo=' + bbsNo을 받기위해 @RequestParam사용한다
	@GetMapping("/detail.do")
	public String detail(@RequestParam(value="bbsNo", required=false, defaultValue="0") int bbsNo, Model model) {
	    // bbsNo를 사용하여 BbsDto 객체를 가져옴.
	    BbsDto bbs = bbsService.getBbs(bbsNo);
	    // 모델에 bbs 객체를 추가하여 뷰에서 사용할 수 있도록 합니다.
	    model.addAttribute("bbs", bbs);
	    // "bbs/detail" 뷰를 반환합니다.
	    return "bbs/detail";
	}
	// editor, title, content 3개를 받아야하므로 command 객체로 받는게 남 : BbsDto
	// @RequestParam으로 받을 수 있긴한데 너무 길어짐
	// insert는 redirectAttribute에 저장한 뒤 redirect 한다.
	@PostMapping("/add.do")
	public String add(BbsDto bbs, RedirectAttributes attr) {
		 int addResult = bbsService.addBbs(bbs); 
		 attr.addFlashAttribute("addResult", addResult);
		 return "redirect:/list.do";
	}
	
	//update는 redirectAttribute에 저장한 뒤 redirect한다.
	// editor, title, content 3개를 받아야하므로 command 객체로 받는게 남 : BbsDto
	@PostMapping("/modify.do")
	public String modify(BbsDto bbs, RedirectAttributes attr) {
		int modifyResult = bbsService.modifyBbs(bbs);
		attr.addFlashAttribute("modifyResult", modifyResult);
		return "redirect:/detail.do?bbsNo=" + bbs.getBbsNo();
	}
	
	//delete는 redirectAttribute에 저장한 뒤 redirect한다.
	@PostMapping("/remove.do")
	public String remove(@RequestParam(value="bbsNo", required=false, defaultValue="0") int bbsNo
						, RedirectAttributes attr) {
		int deleteResult = bbsService.deleteBbs(bbsNo);
		attr.addFlashAttribute("deleteResult", deleteResult);
		return "redirect:/list.do";
		
	}
	
}
