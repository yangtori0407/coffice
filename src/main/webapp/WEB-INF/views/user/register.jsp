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
<link rel="stylesheet" type="text/css" href="/css/user/register.css">
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">
				
					
					<form:form modelAttribute="userVO" cssClass="user" action="/user/register" method="post" enctype="multipart/form-data">
					  <h3 class="title">Coffice 사원 등록</h3>
					
					  <div class="input-wrap">
					    <input type="text" placeholder="사원번호는 자동으로 생성됩니다." readonly>  
					  </div>
					
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
					    <input type="text" id="name" name="name" placeholder="이름">
					    <div>
			               <form:errors path="name" cssClass="error-message"/>
                        </div>   
					  </div>
					
					  <div class="input-wrap">
					    <input type="text" id="email" name="email" placeholder="이메일">
					    <div>
			               <form:errors path="email" cssClass="error-message"/>
                        </div>   
					  </div>
					
					  <div class="input-wrap">
					    <input type="text" id="phone" name="phone" placeholder="전화번호">
					    <div>
			               <form:errors path="phone" cssClass="error-message"/>
                        </div>  
					  </div>
					
					  <div class="input-wrap">
					    <label for="deptId">부서</label>
					    <select id="deptId" name="deptId">
					      <option value="1">인사팀</option>
					      <option value="2">연구개발팀</option>
					      <option value="3">물류팀</option>
					      <option value="4">영업팀</option>
					      <option value="5">회계팀</option>
					    </select>
					  </div>
					
					  <div class="input-wrap">
					    <label for="position">직급</label>
					    <select id="position" name="position">
					      <option value="부장">부장</option>
					      <option value="차장">차장</option>
					      <option value="과장">과장</option>
					      <option value="대리">대리</option>
					      <option value="주임">주임</option>
					      <option value="사원">사원</option>
					    </select>
					  </div>
					
					  <div class="input-wrap">
					    <label>입사 구분</label>
					    <label><input type="radio" name="hireType" value="1" checked> 경력</label>
					    <label><input type="radio" name="hireType" value="0"> 신입</label>
					  </div>
					  
					  <div class="input-wrap">
					    <label>재직 여부</label>
					    <label><input type="radio" name="status" value="1" checked> 재직</label>
					  </div>
					
					  <div class="input-wrap">
					    <label for="birthDate">생년월일</label>
					    <input type="date" id="birthDate" name="birthDate">
					    <form:errors path="birthDate" cssClass="error-message" />
					  </div>
					
					  <div class="input-wrap">
					    <label for="hireDate">입사일</label>
					    <input type="date" id="hireDate" name="hireDate">
					  </div>
					
					  <div class="input-wrap">
					    <label for="file">프로필 이미지</label>
					    <input type="file" id="file" name="file">
					  </div>
					
					  <input type="submit" class="btn" value="등록">
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