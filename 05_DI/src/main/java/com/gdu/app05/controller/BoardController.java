package com.gdu.app05.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdu.app05.service.BoardService;
import com.gdu.app05.service.BoardServiceImpl;

@Controller
public class BoardController {
	
	//BoardServiceImpl에서 Component 작성 후 Autowired. 3가지 방법이 있음 택 1 해서 사용

	//주입된 boardService 객체의 변경 방지를 위한 final 처리한다.
	private final BoardService boardService;

	//boardService에 final 처리를 하면 생성자 주입만 가능하다(필드 주입과 Setter주입은 불가능하다.)
	//생성자 주입의 @Autowired는 생략할 수 있으므로 @RequiredArgsConstructor와 같은 Anotation으로 대체할 수 있다.
	@Autowired
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
 

//	@Autowired
//	public void setBoardService(BoardService boardService) {
//		this.boardService = boardService;
//	}



	@RequestMapping(value="/board/list.do", method=RequestMethod.GET)
	public String list(Model model) {
	    model.addAttribute("boardList", boardService.getBoardList());
	    return "board/list";
	}
	  
}
