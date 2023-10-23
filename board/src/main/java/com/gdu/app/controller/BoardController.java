package com.gdu.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app.dto.BoardDto;
import com.gdu.app.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

  private final BoardService boardService;
  
  @RequestMapping(value="/board/save.do", method=RequestMethod.POST)
  public String save(BoardDto boardDto
                   , RedirectAttributes redirectAttributes) {
    int addResult = boardService.addBoard(boardDto);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/board/list.do";
  }
  
  
  @RequestMapping(value="/board/list.do", method=RequestMethod.GET)
  public String list(Model model) {
    List<BoardDto> boardList = boardService.getBoardList();
    model.addAttribute("boardList", boardList);
    return "board/list";
  }
  
  @RequestMapping(value="/board/write.do", method=RequestMethod.GET)
  public String write() {
    return "board/write";
  }

  
  @RequestMapping(value="/board/detail.do", method=RequestMethod.GET)
  public String detail(@RequestParam int no, Model model) {
    BoardDto boardDto = boardService.getBoard(no);
    model.addAttribute("board", boardDto);
    return "board/detail";
  }
  
  @RequestMapping(value="/board/delete.do", method=RequestMethod.POST)
  public String delete(@RequestParam int no, RedirectAttributes redirectAttributes) {
	  int deleteResult = boardService.deleteBoard(no);
	  redirectAttributes.addFlashAttribute("deleteResult", deleteResult);
	  return "redirect:/board/list.do";
  }
  
}
