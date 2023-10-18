package com.gdu.app14.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.app14.dto.MemberDto;
import com.gdu.app14.service.MemberService;

import lombok.RequiredArgsConstructor;

/*
 * REST(REpresentational State Transfer)
 * 1. 요청 주소를 작성하는 새로운 방식이다.
 * 2. 변수를 파라미터로 추가하는 Query String 방식이 아니다.
 * 3. 변수를 주소에 포함시키는 Path Variable 방식을 사용한다.
 * 4. "요청 주소(URL) + 요청 방식(Method)"을 합쳐서 요청을 구분한다.
 * 5. 예시
 *          URL         Method
 *  1) 목록 /members    GET        /members/page/1
 *  2) 상세 /members/1  GET
 *  3) 삽입 /members    POST
 *  4) 수정 /members    PUT
 *  5) 삭제 /member/1  DELETE
 */

@RequiredArgsConstructor
@RestController  // 모든 메소드에 @ResponseBody를 추가한다.
public class MemberController {
  
  private final MemberService memberService;

  // 회원 등록을 처리하는 메소드
  // @RequestBody 어노테이션을 사용하여 HTTP 요청 본문에서 MemberDto객체를 추출
  // @RequestBody 어노테이션을 요청 본문을 자바 객체로 매핑하여 사용할 수 있게 함
  @RequestMapping(value="/members", method=RequestMethod.POST, produces="application/json")
  public Map<String, Object> registerMember(@RequestBody MemberDto memberDto, HttpServletResponse response) {
    return memberService.register(memberDto, response);
  }
  
  // 회원 목록 요청
  // 경로에 포함되어 있는 변수 {p}는 @PathVariable을 이용해서 가져올 수 있다.
  // @PathVariable 어노테이션을 사용하여 URL경로의 변수를 가져옴
  // 'p'변수가 optional로 정의되어있어 값이 없을 경우 기본값으로 1을 사용
  @RequestMapping(value="/members/page/{p}", method=RequestMethod.GET, produces="application/json")
  public Map<String, Object> getMembers(@PathVariable(value="p", required=false) Optional<String> opt) {
    int page = Integer.parseInt(opt.orElse("1"));
    return memberService.getMembers(page);
  }
  /*
   *  @PathVariable은 URL경로에서 변수 값을 추출하는데 사용, 
   * 주로 RESTful 웹 애플리케이션에서 클라이언트가 요청한 URL에서 경로 변수 값을 추출하는데 쓰임
   */
  
  
  // 회원 조회 요청
  //@PathVariable 어노테이션을 사용하여 URL 경로의 변수를 가져옴
  
  @RequestMapping(value="/members/{mNo}", method=RequestMethod.GET, produces="application/json")
  public Map<String, Object> getMember(@PathVariable(value="mNo") int memberNo) {
    return memberService.getMember(memberNo);
  }
  
  // 회원 정보 수정 요청
  //@RequestBody 어노테이션을 사용하여 HTTP 요청 본문에서 MemberDto 객체를 추출
  @RequestMapping(value="/members", method=RequestMethod.PUT, produces="application/json")
  public Map<String, Object> modifyMember(@RequestBody MemberDto memberDto) {
    return memberService.modifyMember(memberDto);
  }
  
  //회원 정보 삭제 요청
  @RequestMapping(value="/member/{memberNo}", method=RequestMethod.DELETE, produces="application/json")
  public Map<String, Object> removeMember(@PathVariable(value="memberNo") int memberNo){
	  return memberService.removeMember(memberNo);
  }
  
  //회원들 정보 삭제 요청
  @RequestMapping(value="/member/{memberNoList}", method=RequestMethod.DELETE, produces="application/json")
  public Map<String, Object> removeMembers(@PathVariable(value="memberNoList") String memberNoList){
	  return memberService.removeMembers(memberNoList);
  }
 
  
  
  
  
  
  
}