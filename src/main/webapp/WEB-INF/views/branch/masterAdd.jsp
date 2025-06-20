<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>COFFICE</title>
<link href="/images/3.png" rel="shortcut icon" type="image/x-icon">
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<link rel="stylesheet" type="text/css" href="/css/branch/masterAdd.css">
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">
					<!-- contents 내용 -->
					<div class="form-container">
					    <h2>점주 등록</h2>
						<form:form modelAttribute="branchMasterVO" cssClass="branchMaster" action="./masterAdd" method="post">
							<select class="form-select" id="selectUser" name="userId.userId">
								<option selected>추가할 점주를 선택하세요</option>
								<c:forEach items="${notRegisterBranchMaster}" var="m">
								<option value="${m.userId.userId}">${m.userId.name},${m.userId.userId}</option>
								</c:forEach>						
							</select>
							<div class="input-box">
							    <form:input path="contactNumber" cssClass="form-input" placeholder="사업자등록번호(10자리)" maxlength="10" required="true"/>
							    <span style="cursor:pointer" onclick="code_check();">확인</span>
							</div>
							<div class="error-box">
							    <form:errors path="contactNumber" cssClass="error-message"/>
							</div>
							<div id="serviceKeyHolder" data-servicekey="${servicekey}"></div>
							<div class="input-box">  
							    <form:input path="contactDate" type="date" cssClass="form-input" required="true" max="${today}"/>
							</div>
							<button type="submit" class="btn btn-primary">점주등록</button>
						</form:form>
					</div>
				</div>
			</div>
			<!-- end Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
	</div>
	<!-- End Wrapper -->
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
<script src="/js/branch/masterAdd.js"></script>
</html>