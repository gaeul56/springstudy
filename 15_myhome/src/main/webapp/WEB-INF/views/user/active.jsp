<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="휴면해제" name="title"/>
</jsp:include>

<script src="${contextPath}/resources/js/user_agree.js"></script>

<div>
	<h1>휴면계정안내</h1>
<div>
	안녕하세요. ${sessionScope.inactiveUser.email}님은 1년 이상 로그인하지 않아
	관계 법령에 의해 휴면회원으로 전환되었습니다.
</div>
	<div>
		휴면전환일 ${sessionScope.inactiveUser.inactiveAt}
	</div>
	<div>
		<div>휴면해제를 위해서 휴면해제 버튼</div>
		<div>
			<button type="button" id="btn_active">휴면해제</button>
		</div>
		<script>
		  const fnActive = () => {
			  $('#btn_active').click(()) => {
				  location.href = '${contextPath}/user/active.do';
			  })
		  }
		  fnActive();
		</script>
	</div>

</div>

<%@ include file="../layout/footer.jsp" %>
