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
</head>
  <script>
  $(function(){
	    fnListBoard();
	  })

  function fnListBoard(){
		$('#btn_list').click(function(){
		  location.href = '${contextPath}/board/list.do';
		})
  }
  </script>
<body>

  <div>
    <h1>MVC 작성화면</h1>
    <form method="post" action="${contextPath}/board/save.do">
      <div>작성자</div>
          <input type="text" name="author">
      <div>제목</div>
          <input type="text" name="title">
	  <div>내용</div>
	      <textarea name="content"></textarea> 
      <div>
      <button>저장하기</button>
      <button type="reset">다시작성</button>
      <button type="button" id="btn_list">목록보기</button>
      </div>
    </form>
  </div>

</body>
</html>