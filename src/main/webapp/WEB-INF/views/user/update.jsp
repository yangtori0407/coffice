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
<link rel="stylesheet" type="text/css" href="/css/user/update.css">

 <c:if test="${not empty error}">
  <script>
    alert("${error}");
  </script>
  </c:if>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid" style="height: 650px; padding-top: 17px;">
				
					
					<form:form modelAttribute="userVO" cssClass="user" action="/user/update" method="post" enctype="multipart/form-data">
					  <h3 class="title">Coffice 내 정보 수정</h3>
					  
					  <input type="hidden" name="userId" value="${user.userId}" />
					  
					  <div class="input-wrap">
					    <input type="password" id="password" name="password" placeholder="비밀번호">
					    <div>
			               <form:errors path="password" cssClass="error-message"/>
                        </div>   
					  </div>
					  
					  <div class="input-wrap">
					    <input type="password" id="passwordCheck" name="passwordCheck" placeholder="비밀번호 확인">
					    <div>
			               <form:errors path="passwordCheck" cssClass="error-message"/>
                        </div>   
					  </div>

					  <div class="input-wrap">
					    <input type="text" id="phone" name="phone" placeholder="전화번호" value="${user.phone}">
					    <div>
			               <form:errors path="phone" cssClass="error-message"/>
                        </div>  
					  </div>
					  
					  <div class="input-wrap">
					    <input type="text" id="email" name="email" placeholder="이메일" value="${user.email}">
					    <div>
			               <form:errors path="email" cssClass="error-message"/>
                        </div>   
					  </div>
		
					  <div class="input-wrap">
					    <label for="file">프로필 이미지</label>
					    <input type="file" id="file" name="file">
					  </div>
					
					  <input type="submit" class="btn" value="수정">
					</form:form>

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
</html>