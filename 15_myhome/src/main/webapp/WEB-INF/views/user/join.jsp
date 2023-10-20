<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="회원가입" name="title"/>
</jsp:include>

<script src="${contextPath}/resources/js/user_join.js"></script>
<script>

  /* 함수 호출 */
  $(() => {
	  fnCheckEmail();
	  fnJoin();
  })
  
  /* 전역변수 선언 */
  var emailPassed = false;
  
  
  /* 함수 정의 */
  const fnCheckEmail = () => {
	  $('#btn_get_code').click(() => {
		  
		  let email = $('#email').val();
		  
		  // 연속된 ajax() 함수 호출의 실행 순서를 보장하는 JavaScript 객체 Promise
		  new Promise((resolve, reject) => {
  		  
			 
			  
			  // 성공했다면 resolve() 함수 호출 -> then() 메소드에 정의된 화살표 함수 호출
			  // 실패했다면 reject() 함수 호출 -> catch() 메소드에 정의된 화살표 함수 호출
			  
			  // 1. 정규식 검사
			  let regEmail = /^[A-Za-z0-9-_]+@[A-Za-z0-9]{2,}([.][A-Za-z]{2,6}){1,2}$/;
			  if(!regEmail.test(email)){ //이메일 검사
				  reject(1);	//실패로 보냄
				  return;
			  }
			  
			  // 2. 이메일 중복 체크
			  $.ajax({
				  // 요청
				  type: 'get',
				  url: '${contextPath}/user/checkEmail.do',
				  data: 'email=' + email,
				  // 응답
				  dataType: 'json',
				  success: (resData) => {  // resData === {"enableEmail": true}
					  if(resData.enableEmail){
						  resolve();
					  } else {
						  reject(2); 
					  }
				  }
			  })
			  
		  }).then(() => {
			  
			  // 3. 인증코드 전송
			  $.ajax({
				  //요청
				  type: 'get',
				  url: '${contextPath}/user/sendCode.do',
				  data: 'email=' + email,
				  // 응답
				  dataType: 'json',
				  success: (resData) => { // resData === {"code": "6자리코드"}
				   alert(email + "로 인증코드를 전송했습니다.")
				  $('#code').prop('disabled', false);
				  $('#btn_verify_code').prop('disabled', false);
				  $('#btn_verify_code').click(() => {
				  		emailPassed = $('#code').val() === resData.code;
				  		if(emailPassed){
				  			alert('이메일이 인증되었습니다.');
				  		} else{
				  			alert('이메일 인증이 실패했습니다.');
				  		}
				  	})
					  
				  }
			  })
			  
		  }).catch((state) => {
			  	emailPassed = false;
			  switch(state){
			  case 1: $('#msg_email').text('이메일 형식이 올바르지 않습니다.'); break;
			  case 2: $('#msg_email').text('이미 가입한 이메일입니다. 다른 이메일을 입력해 주세요.'); break;
			  }
		  })
	  })
	  
  }

  const fnJoin = () => {
	  $('#frm_join').submit((ev) => {
		  if(emailPassed){
			  alert('이메일을 인증 받아야합니다.');
			  ev.preventDefault();
			  return;
		  }
	  })
  }
</script>

<div>

  <form id="frm_join" method="post" action="${contextPath}/user/join.do">
    
    <h1>회원가입</h1>
    
    <div>
      <div>
        <label for="email">이메일</label>
        <input type="text" name="email" id="email">
        <button type="button" id="btn_get_code">인증코드받기</button>
        <span id="msg_email"></span>
      </div>
      <div>
        <input type="text" id="code" placeholder="인증코드입력" disabled>
        <button type="button" id="btn_verify_code">인증하기</button>
      </div>
    </div>
    
    <div>
      <input type="hidden" name="event" value="${event}">
      <button type="submit">회원가입하기</button>
    </div>
    
  </form>

</div>

<%@ include file="../layout/footer.jsp" %>