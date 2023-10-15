package com.gdu.app13.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app13.service.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FileController {

  private final FileService fileService; //FileService를 주입받는 생성자 주입을 입한 필드
  
  //파일 업로드를 처리하는 메소드
  @RequestMapping(value="/upload.do", method=RequestMethod.POST)
  public String upload(MultipartHttpServletRequest multipartRequest, RedirectAttributes redirectAttributes) {
    int addResult = fileService.upload(multipartRequest); //업로드 서비스를 호출하여 파일 업로드를 수행
    redirectAttributes.addFlashAttribute("addResult", addResult); //업로드 결과를 Flash 속성으로 추
    return "redirect:/main.do"; //업로드 후에 메인 페이지로리디렉션
  }
  
  //AJAX를 통해 파일 업로드를 처리하는 메소드
  @RequestMapping(value="/ajax/upload.do", method=RequestMethod.POST, produces="application/json")
  @ResponseBody
  public Map<String, Object> ajaxUpload(MultipartHttpServletRequest multipartRequest) {
    return fileService.ajaxUpload(multipartRequest); //AJAX 요청에 대한 업로드 서비스를 호출하고 JSON 응답을 반환
  }
  
}