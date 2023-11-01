package com.gdu.myhome.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.myhome.dao.UserMapper;
import com.gdu.myhome.dto.InactiveUserDto;
import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.util.MyJavaMailUtils;
import com.gdu.myhome.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	
	  private final UserMapper userMapper;
	  private final MySecurityUtils mySecurityUtils;
	  private final MyJavaMailUtils myJavaMailUtils;
	  
	  private final String client_id = "K3HBLQteMesDkdnTQl7_";
	  private final String client_secret = "590mYEK6XK";
	  
	@Override
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email = request.getParameter("email"); //HTTP 요청에서 'email'매개변수를 가져옴
		String pw = mySecurityUtils.getSHA256(request.getParameter("pw")); //'pw' 매개변수를 SHA-256해싱
		
		Map<String, Object> map = Map.of("email", email //'email'과 'pw'를 포함한 매개변수를 지닌 Map을 생성
										,"pw", pw);
		
		HttpSession session = request.getSession();	//현재 세션을 가져오거나 없으면 새로운 세션을 생성
		
		InactiveUserDto inactiveUser = userMapper.getInactiveUser(map);	// 'userMapper'를 사용하여 휴먼 계정 정보를 가져 
		if(inactiveUser != null) { //휴먼 계정 정보가 존재하면 실행
			session.setAttribute("inactiveUser", inactiveUser); //세션에 'inactiveUser' 속성을 설정하여 정보를 저장'
			response.sendRedirect(request.getContextPath() + "/user/active.form"); // active.form 페이지로 이동
		}
		
		UserDto user = userMapper.getUser(map); //userMapper를 사용하여 정상 회원 정보를 가져옴
		
		if(user != null) { // 정상 회원이 없으면 실행 
			request.getSession().setAttribute("user", user); 
			userMapper.insertAccess(email); //사용자의 접속 정보를 기록하는 메서드 호출
			response.sendRedirect(request.getParameter("referer")); //이전 페이지로 이동
		}else {
			response.setContentType("type/html; charset=UTF-8"); //응탑 컨텐츠 타입을 설정한다
			PrintWriter out = response.getWriter(); //PrintWriter 생성
			out.println("<script>");
			out.println("alert('일치하는 회원 정보 없음')");
			out.println("location.href='" + request.getContextPath()+ "/main.do'"); //메인페이지로 이동
			out.println("</script>");
			out.flush();
			out.close();
		}
	}
  
  @Override
  public String getNaverLoginURL(HttpServletRequest request) throws Exception {
    
    // 네이버로그인-1
    // 네이버 로그인 연동 URL 생성하기를 위해 redirect_uri(URLEncoder), state(SecureRandom) 값의 전달이 필요하다.
    // redirect_uri : 네이버로그인-2를 처리할 서버 경로를 작성한다.
    // redirect_uri 값은 네이버 로그인 Callback URL에도 동일하게 등록해야 한다.
    
    String apiURL = "https://nid.naver.com/oauth2.0/authorize";
    String response_type = "code";
    String redirect_uri = URLEncoder.encode("http://localhost:8080" + request.getContextPath() + "/user/naver/getAccessToken.do", "UTF-8");
    String state = new BigInteger(130, new SecureRandom()).toString();
  
    StringBuilder sb = new StringBuilder();
    sb.append(apiURL);
    sb.append("?response_type=").append(response_type);
    sb.append("&client_id=").append(client_id);
    sb.append("&redirect_uri=").append(redirect_uri);
    sb.append("&state=").append(state);
        
    return sb.toString();
    
  }
  
  @Override
  public String getNaverLoginAccessToken(HttpServletRequest request) throws Exception {
    
    // 네이버로그인-2
    // 접근 토큰 발급 요청
    // 네이버로그인-2를 수행하기 위해서는 네이버로그인-1의 응답 결과인 code와 state가 필요하다.
    
    // 네이버로그인-1의 응답 결과(access_token을 얻기 위해 요청 파라미터로 사용해야 함)
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    
    String apiURL = "https://nid.naver.com/oauth2.0/token";
    String grant_type = "authorization_code";  // access_token 발급 받을 때 사용하는 값(갱신이나 삭제시에는 다른 값을 사용함)
    
    StringBuilder sb = new StringBuilder();
    sb.append(apiURL);
    sb.append("?grant_type=").append(grant_type);
    sb.append("&client_id=").append(client_id);
    sb.append("&client_secret=").append(client_secret);
    sb.append("&code=").append(code);
    sb.append("&state=").append(state);
    
    // 요청
    URL url = new URL(sb.toString());
    HttpURLConnection con = (HttpURLConnection)url.openConnection();
    con.setRequestMethod("GET");  // 반드시 대문자로 작성
    
    // 응답
    BufferedReader reader = null;
    int responseCode = con.getResponseCode();
    if(responseCode == 200) {
      reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
    } else {
      reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
    }
    
    String line = null;
    StringBuilder responseBody = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      responseBody.append(line);
    }
    
    JSONObject obj = new JSONObject(responseBody.toString());
    return obj.getString("access_token");
    
  }
  
  @Override
  public UserDto getNaverProfile(String accessToken) throws Exception {
    
    // 네이버 로그인-3
    // 접근 토큰을 전달한 뒤 사용자의 프로필 정보(이름, 이메일, 성별, 휴대전화번호) 받아오기
    // 요청 헤더에 Authorization: Bearer accessToken 정보를 저장하고 요청함
    
    // 요청
	  String apiURL = "https://openapi.naver.com/v1/nid/me"; // Naver API의 사용자 정보를 가져올 엔드포인트 URL입니다.
	  URL url = new URL(apiURL); // URL 객체를 생성하여 Naver API 서버에 연결할 URL을 설정합니다.
	  HttpURLConnection con = (HttpURLConnection)url.openConnection(); // HTTP 연결을 열고 HttpURLConnection 객체를 생성합니다.

	  con.setRequestMethod("GET"); // HTTP GET 요청 메서드를 설정합니다.
	  con.setRequestProperty("Authorization", "Bearer " + accessToken); // 요청 헤더에 Naver API에 대한 액세스 토큰을 추가합니다.

	  // 응답
	  
	  /* BufferedReader는 사용자가 요청할 때마다 데이터를 읽어오는 것이 아닌 일정한 크기의 데이터를 한번에 읽어와 버퍼에 보관한 후,
	   * 사용자의 요청이 있을 때 버퍼에서 데이터를 읽어오는 방식으로 동작
	   */
	  BufferedReader reader = null; // 서버 응답을 읽을 BufferedReader를 초기화합니다.
	  int responseCode = con.getResponseCode(); // HTTP 응답 코드를 가져옵니다.

	  if (responseCode == 200) { // HTTP 응답 코드가 200 (OK) 인 경우
	      reader = new BufferedReader(new InputStreamReader(con.getInputStream())); // 성공적인 응답인 경우, 입력 스트림을 사용하여 서버 응답을 읽습니다.
	  } else { // HTTP 응답 코드가 200이 아닌 경우 (에러)
	      reader = new BufferedReader(new InputStreamReader(con.getErrorStream())); // 에러 응답인 경우, 에러 스트림을 사용하여 에러 메시지를 읽습니다.
	  }

	  String line = null; // 한 줄씩 읽기 위한 문자열 변수를 초기화합니다.
	  StringBuilder responseBody = new StringBuilder(); // 응답 내용을 저장할 StringBuilder를 초기화합니다.

	  while ((line = reader.readLine()) != null) { // 서버 응답을 한 줄씩 읽어서
	      responseBody.append(line); // 응답 내용을 StringBuilder에 추가합니다.
	  }

	  // 응답 결과(프로필을 JSON으로 응답) -> UserDto 객체
	  JSONObject obj = new JSONObject(responseBody.toString()); // 서버 응답을 JSON 객체로 변환합니다.
	  JSONObject response = obj.getJSONObject("response"); // JSON 응답에서 "response" 객체를 가져옵니다.
	  UserDto user = UserDto.builder() // UserDto 객체를 생성하고 사용자 정보를 설정합니다.
	                  .email(response.getString("email"))
	                  .name(response.getString("name"))
	                  .gender(response.getString("gender"))
	                  .mobile(response.getString("mobile"))
	                  .build();

	  return user; // UserDto 객체를 반환합니다.
    
  }
  @Override
  public UserDto getUser(String email) {
	  return userMapper.getUser(Map.of("email", email));
	}
  @Override
  public void naverJoin(HttpServletRequest request, HttpServletResponse response) {

      // 요청 파라미터에서 사용자 정보 추출
      String email = request.getParameter("email"); // 이메일 추출
      String name = mySecurityUtils.preventXSS(request.getParameter("name")); // 이름 추출 (XSS 공격 방지)
      String gender = request.getParameter("gender"); // 성별 추출
      String mobile = request.getParameter("mobile"); // 휴대폰 번호 추출
      String event = request.getParameter("event"); // 이벤트 여부 추출

      // UserDto 객체 생성
      UserDto user = UserDto.builder()
                      .email(email)
                      .name(name)
                      .gender(gender)
                      .mobile(mobile.replace("-", "")) // 하이픈 제거
                      .agree(event != null ? 1 : 0) // 이벤트 동의 여부 설정
                      .build();

      // 네이버 간편가입 처리 및 결과 반환
      int naverJoinResult = userMapper.insertNaverUser(user); // 네이버 간편가입을 수행하고 결과를 받습니다.

      try {
          // 응답 설정
          response.setContentType("text/html; charset=UTF-8");
          PrintWriter out = response.getWriter();
          out.println("<script>");

          if (naverJoinResult == 1) { // 네이버 간편가입 성공한 경우
              request.getSession().setAttribute("user", userMapper.getUser(Map.of("email", email))); // 사용자 정보 세션 저장
              userMapper.insertAccess(email); // 사용자 접속 정보 기록
              out.println("alert('네이버 간편가입이 완료되었습니다.')"); // 성공 메시지 출력
          } else {
              out.println("alert('네이버 간편가입이 실패했습니다.')"); // 실패 메시지 출력
          }

          out.println("location.href='" + request.getContextPath() + "/main.do'"); // 메인 페이지로 이동
          out.println("</script>");
          out.flush();
          out.close();

      } catch (Exception e) {
          e.printStackTrace();
      }
  }


  @Override
  public void naverLogin(HttpServletRequest request, HttpServletResponse response, UserDto naverProfile) throws Exception {
      String email = naverProfile.getEmail(); // 네이버 프로필에서 이메일 추출

      // 이메일을 기반으로 사용자 정보 조회
      UserDto user = userMapper.getUser(Map.of("email", email)); // 이메일을 사용하여 사용자 정보를 데이터베이스에서 조회

      if (user != null) { // 사용자 정보가 존재하는 경우
          request.getSession().setAttribute("user", user); // 사용자 정보를 세션에 저장
          userMapper.insertAccess(email); // 사용자의 접속 기록을 데이터베이스에 기록
      } else { // 사용자 정보가 없는 경우
          response.setContentType("text/html; charset=UTF-8"); // 응답 콘텐츠 타입 설정
          PrintWriter out = response.getWriter(); // 응답을 출력할 PrintWriter 생성
          out.println("<script>"); // JavaScript 코드 시작
          out.println("alert('일치하는 회원 정보가 없습니다.')"); // 알림 메시지 출력
          out.println("location.href='" + request.getContextPath() + "/main.do'"); // 메인 페이지로 이동
          out.println("</script>"); // JavaScript 코드 종료
          out.flush(); // 출력 버퍼 비우기
          out.close(); // PrintWriter 닫기
      }
  }

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    
    HttpSession session = request.getSession();
    
    session.invalidate();
    
    try {
      response.sendRedirect(request.getContextPath() + "/main.do");
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Transactional(readOnly=true)
  @Override
  public ResponseEntity<Map<String, Object>> checkEmail(String email) {
    
    Map<String, Object> map = Map.of("email", email);
    
    boolean enableEmail = userMapper.getUser(map) == null
                       && userMapper.getLeaveUser(map) == null
                       && userMapper.getInactiveUser(map) == null;
    
    return new ResponseEntity<>(Map.of("enableEmail", enableEmail), HttpStatus.OK);
    
  }
  
  @Override
  public ResponseEntity<Map<String, Object>> sendCode(String email) {
    
    // RandomString 생성(6자리, 문자 사용, 숫자 사용)
    String code = mySecurityUtils.getRandomString(6, true, true);
    
    // 메일 전송
    myJavaMailUtils.sendJavaMail(email
                               , "myhome 인증 코드"
                               , "<div>인증코드는 <strong>" + code + "</strong>입니다.</div>");
    
    return new ResponseEntity<>(Map.of("code", code), HttpStatus.OK);
    
  }
  
  @Override
  public void join(HttpServletRequest request, HttpServletResponse response) {

      // 요청 파라미터에서 사용자 정보 추출
      String email = request.getParameter("email"); // 이메일 추출
      String pw = mySecurityUtils.getSHA256(request.getParameter("pw")); // 비밀번호 추출 및 SHA-256 해싱
      String name = mySecurityUtils.preventXSS(request.getParameter("name")); // 이름 추출 (XSS 공격 방지)
      String gender = request.getParameter("gender"); // 성별 추출
      String mobile = request.getParameter("mobile"); // 휴대폰 번호 추출
      String postcode = request.getParameter("postcode"); // 우편번호 추출
      String roadAddress = request.getParameter("roadAddress"); // 도로명 주소 추출
      String jibunAddress = request.getParameter("jibunAddress"); // 지번 주소 추출
      String detailAddress = mySecurityUtils.preventXSS(request.getParameter("detailAddress")); // 상세 주소 추출 (XSS 공격 방지)
      String event = request.getParameter("event"); // 이벤트 여부 추출

      // UserDto 객체 생성
      UserDto user = UserDto.builder()
                      .email(email)
                      .pw(pw)
                      .name(name)
                      .gender(gender)
                      .mobile(mobile)
                      .postcode(postcode)
                      .roadAddress(roadAddress)
                      .jibunAddress(jibunAddress)
                      .detailAddress(detailAddress)
                      .agree(event.equals("on") ? 1 : 0) // 이벤트 동의 여부가 int 로 설정되어있음 그래서 on이면 1 아니면 0
                      .build();

      // 회원 가입 처리 및 결과 반환
      int joinResult = userMapper.insertUser(user); // 회원 가입을 수행하고 결과를 받습니다.

      try {
          // 응답 설정
          response.setContentType("text/html; charset=UTF-8");
          PrintWriter out = response.getWriter();
          out.println("<script>");

          if (joinResult == 1) { // 회원 가입 성공한 경우
              request.getSession().setAttribute("user", userMapper.getUser(Map.of("email", email))); // 사용자 정보 세션 저장
              userMapper.insertAccess(email); // 사용자의 접속 기록을 데이터베이스에 기록
              out.println("alert('회원 가입되었습니다.')"); // 성공 메시지 출력
              out.println("location.href='" + request.getContextPath() + "/main.do'"); // 메인 페이지로 이동
          } else {
              out.println("alert('회원 가입이 실패했습니다.')"); // 실패 메시지 출력
              out.println("history.go(-2)"); // 이전 페이지로 이동
          }

          out.println("</script>");
          out.flush();
          out.close();

      } catch (Exception e) {
          e.printStackTrace();
      }
  }


  @Override
  public ResponseEntity<Map<String, Object>> modify(HttpServletRequest request) {
	  
    // 요청 파라미터에서 사용자 정보 추출
    String name = mySecurityUtils.preventXSS(request.getParameter("name"));
    String gender = request.getParameter("gender");
    String mobile = request.getParameter("mobile");
    String postcode = request.getParameter("postcode");
    String roadAddress = request.getParameter("roadAddress");
    String jibunAddress = request.getParameter("jibunAddress");
    String detailAddress = mySecurityUtils.preventXSS(request.getParameter("detailAddress"));
    String event = request.getParameter("event");
    int agree = event.equals("on") ? 1 : 0; //이벤트 동의 여부가 on이면 1 아니면 0
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    
    // UserDto 생성
    UserDto user = UserDto.builder()
        .name(name)
        .gender(gender)
        .mobile(mobile)
        .postcode(postcode)
        .roadAddress(roadAddress)
        .jibunAddress(jibunAddress)
        .detailAddress(detailAddress)
        .agree(agree)
        .userNo(userNo)
        .build();
    
    //userMapper의 updateUser 사용, 위에 생성한 UserDto로 update 하고 그 결과를 modifyResult에 저장
    int modifyResult = userMapper.updateUser(user);
    
    //modifyResult가 1이면 정보를 업데이트함
    if(modifyResult == 1) {
      HttpSession session = request.getSession();
      UserDto sessionUser = (UserDto)session.getAttribute("user");
      sessionUser.setName(name);
      sessionUser.setGender(gender);
      sessionUser.setMobile(mobile);
      sessionUser.setPostcode(postcode);
      sessionUser.setRoadAddress(roadAddress);
      sessionUser.setJibunAddress(jibunAddress);
      sessionUser.setDetailAddress(detailAddress);
      sessionUser.setAgree(agree);
    }
    //modifyResult"와 해당 값을 HttpStatus.OK 상태 코드와 함께 응답합니다. 클라이언트는 이 응답을 받고 "modifyResult" 키를 통해 결과를 확인할 수 있습니다.
    return new ResponseEntity<>(Map.of("modifyResult", modifyResult), HttpStatus.OK);
    
  }

  @Override
  public void modifyPw(HttpServletRequest request, HttpServletResponse response) {
    
    String pw = mySecurityUtils.getSHA256(request.getParameter("pw"));
    
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    
    UserDto user = UserDto.builder()
                    .pw(pw)
                    .userNo(userNo)
                    .build();
    
    int modifyPwResult = userMapper.updateUserPw(user);
    
    try {
      
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(modifyPwResult == 1) {
        HttpSession session = request.getSession();
        UserDto sessionUser = (UserDto)session.getAttribute("user");
        sessionUser.setPw(pw);
        out.println("alert('비밀번호가 수정되었습니다.')");
        out.println("location.href='" + request.getContextPath() + "/user/mypage.form'");
      } else {
        out.println("alert('비밀번호가 수정되지 않았습니다.')");
        out.println("history.back()");
      }
      out.println("</script>");
      out.flush();
      out.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Override
  public void leave(HttpServletRequest request, HttpServletResponse response) {
  
    Optional<String> opt = Optional.ofNullable(request.getParameter("userNo"));
    int userNo = Integer.parseInt(opt.orElse("0"));
    
    UserDto user = userMapper.getUser(Map.of("userNo", userNo));
    
    if(user == null) {
      try {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('회원 탈퇴를 수행할 수 없습니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.do'");
        out.println("</script>");
        out.flush();
        out.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    int insertLeaveUserResult = userMapper.insertLeaveUser(user);
    int deleteUserResult = userMapper.deleteUser(user);
    
   try {
      
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(insertLeaveUserResult == 1 && deleteUserResult == 1) {
        HttpSession session = request.getSession();
        session.invalidate();
        out.println("alert('회원 탈퇴되었습니다. 그 동안 이용해 주셔서 감사합니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.do'");
      } else {
        out.println("alert('회원 탈퇴되지 않았습니다.')");
        out.println("history.back()");
      }
      out.println("</script>");
      out.flush();
      out.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Override
  public void inactiveUserBatch() {
    userMapper.insertInactiveUser();
    userMapper.deleteUserForInactive();
  }
  
  @Override
  public void active(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
  
    InactiveUserDto inactiveUser = (InactiveUserDto)session.getAttribute("inactiveUser");
    String email = inactiveUser.getEmail();
    
    int insertActiveUserResult = userMapper.insertActiveUser(email);
    int deleteInactiveUserResult = userMapper.deleteInactiveUser(email);
    
    try {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(insertActiveUserResult == 1 && deleteInactiveUserResult == 1) {
        out.println("alert('휴면계정이 복구되었습니다. 계정 활성화를 위해서 곧바로 로그인 해 주세요.')");
        out.println("location.href='" + request.getContextPath() + "/main.do'");  // 로그인 페이지로 보내면 로그인 후 다시 휴면 계정 복구 페이지로 돌아오므로 main으로 이동한다.
      } else {
        out.println("alert('휴면계정이 복구가 실패했습니다. 다시 시도하세요.')");
        out.println("history.back()");
      }
      out.println("</script>");
      out.flush();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
}