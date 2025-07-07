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
				
					
					<form:form modelAttribute="employeeVO" cssClass="employee" action="/employee/update" method="post" enctype="multipart/form-data">
					  <h3 class="title">Coffice 사원 수정</h3>
						
					  <input type="hidden" name="userId" value="${employeeVO.userId}" />
					
					   <div class="input-wrap">
						  <form:input path="name" cssClass="form-control" />
						  <form:errors path="name" cssClass="error-message" />
						</div>
						
						<div class="input-wrap">
						  <form:input path="email" cssClass="form-control" />
						  <form:errors path="email" cssClass="error-message" />
						</div>
						
						<div class="input-wrap">
						  <form:input path="phone" cssClass="form-control" />
						  <form:errors path="phone" cssClass="error-message" />
						</div>

					
					  <div class="input-wrap">
					    <label for="deptId" id="deptId" >부서</label>
					    <form:select path="deptId" >
					      <form:option value="1">인사팀</form:option>
					      <form:option value="2">연구개발팀</form:option>
					      <form:option value="3">물류팀</form:option>
					      <form:option value="4">영업팀</form:option>
					      <form:option value="5">회계팀</form:option>
					    </form:select>
					  </div>
					
					  <div class="input-wrap">
					    <label for="position" id="position">직급</label>
					    <form:select path="position">
					      <form:option value="부장">부장</form:option>
					      <form:option value="차장">차장</form:option>
					      <form:option value="과장">과장</form:option>
					      <form:option value="대리">대리</form:option>
					      <form:option value="주임">주임</form:option>
					      <form:option value="사원">사원</form:option>
					    </form:select>
					  </div>
					
					  <div class="input-wrap">
					    <label>입사 구분</label>
					     <label>
						    <form:radiobutton path="hireType" value="1"/> 경력
						  </label>
						  <label>
						    <form:radiobutton path="hireType" value="0"/> 신입
						  </label>
					  </div>
					  
					  <div class="input-wrap">
					    <label>재직 여부</label>
					     <label>
						    <form:radiobutton path="status" value="1"/> 재직
						  </label>
						  <label>
						    <form:radiobutton path="status" value="0"/> 퇴사 (* 퇴사일자 등록 필수)
						  </label>
					  </div>
					  
					  <div class="input-wrap">
					    <label for="birthDate">퇴사일</label>
					    <input type="date" id="resignDate" name="resignDate">
					    <form:errors path="resignDate" cssClass="error-message" />
					  </div>
					
					  <div class="input-wrap">
					    <label for="birthDate">생년월일</label>
					    <input type="date" id="birthDate" name="birthDate">
					    <form:errors path="birthDate" cssClass="error-message" />
					  </div>
					
					  <div class="input-wrap">
					    <label for="hireDate">입사일</label>
					    <input type="date" id="hireDate" name="hireDate" value="${employee.hireDate}">
					  </div>
					
					  <div class="input-wrap">
					    <label for="file">프로필 이미지</label>
					    <input type="file" id="file" name="file" value="${employee.saveName}">
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