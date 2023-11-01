<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<style>
	.bbs{
		width: 300px;
		border: 1px solid gray;
		cursor: pointer;
	}
</style>
</head>
    <h1> 작성화면 </h1>
    <div>
    	<form id="frm_add" method="post" action="${contextPath}/add.do">
    	  <div><input type="text" name="editor" id="editor" placeholder="작성자"></div>
    <!-- required = "required" 입력 필수
    	  <input type="text" name="title" id="title" placeholder="제목" required>
	--> 
    	  <div><input type="text" name="title" id="title" placeholder="제목"></div> 
    	  <div><input type="text" name="contents" id="contents" placeholder="내용"></div>
    	  <div><button type="submit">등록하기</button></div>
    	</form>
    </div>
    
 	<hr>
 	
 	<h1> 목록보기 </h1>
	<h3> 전체 개수: ${total}</h3>
	<c:forEach items="${bbsList}" var="bbs">
		<div class="bbs" data-bbs_no="${bbs.bbsNo}">
		  <div>${bbs.bbsNo}</div>
		  <div>${bbs.title}</div>		  
		</div>
	</c:forEach>
	<div class="paging">${paging}</div>	
<script>
	 /*
	 $('.bbs').click(function(){
		// 클릭한 대상: 이벤트 대상
		let bbsNo = $(this).data('bbs_no');
		alert(bbsNo);
	})
	*/
	 $('.bbs').click((ev) => {
		 //클릭한 대상: 이벤트 대상(이벤트 객체의 target 속)
		 let bbsNo = $(ev.target).parent().data('bbs_no'); 
	 	 
	 
	     // 1. 요청하고 controller가 @GetMapping("/detail.do")으로 받음
	 	 // 2. controller가 서비스로 전달하고, 서비스가 매퍼로 전달
	 	 // 3. 매퍼가 파라미터로 받아서 bbsMapper.xml로 전달
	 	 // 4. 매퍼가 다시 값을 순서대로 반환함 
	 	 //?bbsNo=' + bbsNo을 받기위해 controller에서 @RequestParam사용
		 //location.href = `${contextPath}/detail.do?bbsNo=${bbsNo}`;
		 location.href ='${contextPath}/detail.do?bbsNo=' + bbsNo;
	 })		
	 
	 	//$(같은 함수) 두번이나 부를 경우 성능을 떨어트리므로 let title로 변수 선언해줌 
	 $('#frm_add').submit((ev) => {
		 let title = $('#title');
		 if($('#title').val() === ''){
			 alert('제목은 필수입니다.');
			 title.focus();
			 ev.preventDefault();
			 return;
		 }
	 })
	 
	 const addResult = '${addResult}'; // '', '1', '0'
	 if(addResult !== ''){
		 if(addResult === '1'){
			 alert('추가 성공!');
		 } else{
			 alert('추가 실패ㅠ_ㅠ');
		 }
	 }
	 
	 const deleteResult = '${deleteResult}'; // '', '1', '0'
	 if(deleteResult !== ''){
		 if(deleteResult === '1'){
			 alert('삭제 성공!');
		 } else{
			 alert('삭제 실패ㅠ_ㅠ');
		 }
	 }
	  	
	  	
</script>
</body>
</html>