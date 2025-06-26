<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>COFFICE</title>
<link href="/images/3.png" rel="shortcut icon" type="image/x-icon">
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<link rel="stylesheet" type="text/css" href="/css/employee/detail.css">

</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">
	
					<div class="main d-flex justify-content-center align-items-center">
					  <div class="card" style="width: 550px;">
					    <div class="card-header py-2 d-flex justify-content-between align-items-center">
					      <h6 class="m-0 font-weight-bold text-primary">${employeeVO.name} 님의 정보</h6>
					      <a href="/employee/update?userId=${employeeVO.userId}" class="btn btn-outline-primary">수정</a>
					    </div>
					    <div class="card-body text-center">
					      <c:choose>
					        <c:when test="${not empty employeeVO.saveName}">
					          <img src="/coffice/upload/profile/${employeeVO.saveName}" class="circle-img" alt="Profile Image">
					        </c:when>
					        <c:otherwise>
					          <img src="/images/coffice.png" class="circle-img" alt="Default Profile">
					        </c:otherwise>
					      </c:choose>
					
					      <div class="employee-name">${employeeVO.name} 님</div>
					      <br>
					      <div class="employee-info"><ion-icon name="business-outline"></ion-icon> 부서 : ${employeeVO.deptName}</div>
					      <div class="employee-info"><ion-icon name="medal-outline"></ion-icon> 직급 : ${employeeVO.position}</div>
					      <div class="employee-info"><ion-icon name="finger-print-outline"></ion-icon> 사원번호 : ${employeeVO.userId}</div>
					      <div class="employee-info"><ion-icon name="call-outline"></ion-icon> 전화번호 : ${employeeVO.phone}</div>
					      <div class="employee-info"><ion-icon name="mail-outline"></ion-icon> 이메일 : ${employeeVO.email}</div>
					      <div class="employee-info"><ion-icon name="calendar-outline"></ion-icon> 입사일 : ${employeeVO.hireDate}</div>
					      <div class="employee-info"><ion-icon name="gift-outline"></ion-icon> 생년월일 : ${employeeVO.birthDate}</div>
					      <div class="employee-info">
					        <ion-icon name="briefcase-outline"></ion-icon> 입사구분 :
					        <c:choose>
					          <c:when test="${employeeVO.hireType == 1}">경력</c:when>
					          <c:otherwise>신입</c:otherwise>
					        </c:choose>
					      </div>
					      <div class="employee-info">
					        <ion-icon name="person-outline"></ion-icon> 재직여부 :
					        <c:choose>
					          <c:when test="${employeeVO.status == 1}">재직중</c:when>
					          <c:otherwise>퇴사</c:otherwise>
					        </c:choose>
					      </div>
					      <c:if test="${employeeVO.status == 0}">
							  <div class="employee-info">
							    <ion-icon name="calendar-outline"></ion-icon>
							    퇴사 일자 : ${employeeVO.resignDate}
							  </div>
						</c:if>
					    </div>
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
	<script src="/js/user/mypage.js"></script>
	<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</body>
</html>