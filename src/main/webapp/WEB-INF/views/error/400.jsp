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
</head>
<body id="page-top">
	<div id="wrapper">
	<sec:authorize access="isAuthenticated()">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>	
	</sec:authorize>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">
					<div class="text-center">
                        <div class="error mx-auto" data-text="400">400</div>
                        <p class="lead text-gray-800 mb-3" style="font-size: 24px;">잘못된 요청입니다</p>
    					<p class="text-gray-500 mb-2">서버가 요청을 이해할 수 없습니다.<br>URL이 잘못되었거나, 형식이 맞지 않는 요청일 수 있습니다.</p>
    					<p class="text-gray-500 mb-4">요청 내용을 다시 확인해 주세요.</p>
                        <sec:authorize access="isAuthenticated()">
                        <a href="/" style="color: #3498db;">&larr; 대시보드로 돌아가기</a>
                        </sec:authorize>
                        <sec:authorize access="!isAuthenticated()">
                        <a href="/" style="color: #3498db;">&larr; 로그인하러 가기</a>
                        </sec:authorize>
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
</html>