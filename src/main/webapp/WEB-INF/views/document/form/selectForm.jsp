<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>COFFICE</title>
<link href="/images/3.png" rel="shortcut icon" type="image/x-icon">
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">

					<!-- contents 내용 -->
					<c:forEach items="${formList}" var="i">						
						<div class="formLinks m-2" style="border: 1px solid black; width: 100px;" 
						data-form-id="${i.formId}" >${i.name}</div>
					</c:forEach>
					
					<br>
					<div>${sessionScope.userVO.name}님 접속 중</div>
					<br>
					<div>
						<a href="./tempLogin1"><button>브라움 사원 로그인</button></a>
						<a href="./tempLogin2"><button>소라카 대리 로그인</button></a>
						<a href="./tempLogin3"><button>소나 과장 로그인</button></a>
						<a href="./tempLogin4"><button>쓰레쉬 부장 로그인</button></a>
						<a href="./tempLogout"><button>로그아웃</button></a>
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
	
	<script src="/js/document/formSelector.js"></script>
	
</body>
</html>