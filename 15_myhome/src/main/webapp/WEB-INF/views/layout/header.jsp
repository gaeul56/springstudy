<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${contextPath}/resources/css/init.css?dt=${dt}" />
<link rel="stylesheet" href="${contextPath}/resources/css/header.css?dt=${dt}" />
<link rel="stylesheet" href="${contextPath}/resources/css/main.css?dt=${dt}" />
<link rel="stylesheet" href="${contextPath}/resources/css/footer.css?dt=${dt}" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/40.0.0/classic/ckeditor.js"></script>

</head>
<body></body>

  <div class="hearder_wrap">
    <div class="logo"></div>
    <div class="login_wrap">
    	<c:if test="${true}">
    		<a href="${contextPath}/user/login.form">로그인</a>
    	</c:if>
    	<c:if test:="${false}">
    		님 환영합니다
    	</c:if>
    </div>
 	<div class="gnb_wrap">
 		<ul class="gnb">
 			<li><a href="${contextPath}">댓글형게시판</a></li>
 			<li><a href="${contextPath}">계층게시판</a></li>
 			<li><a href="${contextPath}">첨부게시판</a></li>
  			<li><a href="${contextPath}">MY</a></li>			
 		</ul>
 	</div>
  </div>