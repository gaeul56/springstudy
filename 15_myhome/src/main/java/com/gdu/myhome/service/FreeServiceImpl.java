package com.gdu.myhome.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.myhome.dao.FreeMapper;
import com.gdu.myhome.dto.FreeDto;
import com.gdu.myhome.util.MyPageUtils;
import com.gdu.myhome.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@Transactional //댓글 삽입이랑 업데이트 동시에 진행하니깐 필요
@RequiredArgsConstructor
@Service
public class FreeServiceImpl implements FreeService {

  private final FreeMapper freeMapper;
  private final MyPageUtils myPageUtils;
  private final MySecurityUtils mySecurityUtils;
  
  @Override
  public int addFree(HttpServletRequest request) {
    
    String email = request.getParameter("email");
    String contents = mySecurityUtils.preventXSS(request.getParameter("contents"));
    
    FreeDto free = FreeDto.builder()
                    .email(email)
                    .contents(contents)
                    .build();
    
    return freeMapper.insertFree(free);
    
  }
  @Transactional(readOnly = true)
  @Override
  public void loadFreeList(HttpServletRequest request, Model model) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    
    int display = 10;
    
    int total = freeMapper.getFreeCount();
    
    myPageUtils.setPaging(page, total, display);
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<FreeDto> freeList = freeMapper.getFreeList(map);
    
    model.addAttribute("freeList", freeList);
    model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/free/list.do"));
    model.addAttribute("beginNo", total - (page - 1) * display);
    
  }
  
 /*
  * 댓글달기
  * 
  *		댓글 정보 (Editor, Contents)
  *		원글 정보 (Depth, Group_no, Group_order)
  *		
  *		원글 DTO
  *		기존댓글업데이트 (원글DTO)
  *
  *		댓글 DTO
  *		댓글 삽입 (댓글DTO)
  */
  
  
  @Override
	public int addReply(HttpServletRequest request) {
	  
		// 요청 파라미터
	  	// 댓글 정보(EMAIL, CONTENTS)
	    // 원글 정보(DEPTH, GROUP_NO, GROUP_ORDER)
	  String email = request.getParameter("email");
	  String contents = request.getParameter("contents");
	  int depth = Integer.parseInt(request.getParameter("depth"));
	  int groupNo = Integer.parseInt(request.getParameter("groupNo"));
	  int groupOrder = Integer.parseInt(request.getParameter("groupOrder"));
	  
	  	// 원글 DTO
	  	// 기존댓글업데이트(원글 DTO)
	  FreeDto free = FreeDto.builder()
			  			.groupNo(groupNo)
			  			.groupOrder(groupOrder)
			  			.build();
	  freeMapper.updateGroupOrder(free);
	  
	  	//댓글 DTO
	  	//댓글삽입(댓글DTO)
	  FreeDto reply = FreeDto.builder()
			  			.email(email)
			  			.contents(contents)
			  			.depth(depth + 1) 
			  			.groupNo(groupNo)
			  			.groupOrder(groupOrder + 1)
			  			.build();
	  int addReplyResult = freeMapper.insertReply(reply);
	  
	  
	  return 0;
	}
  
}
