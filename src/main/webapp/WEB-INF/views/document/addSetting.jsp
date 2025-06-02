<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>COFFICE</title>
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
<script type="module"
	src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule
	src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">

					<!-- contents 내용 -->

					<div class="row" style="height: 1000px;">
					
						<div class="col-6 mb-3">
							<div style="height: 200px; border: solid black 1px;">양식 미리보기</div>
						</div>
						
						<div class="col-6 mb-3">
							<div style="height: 200px; border: solid black 1px;">양식 리스트</div>
						</div>
						
						
						<div class="col-6 mb-3">
							<div style="height: 200px; border: solid black 1px;">사원 목록 창</div>
						</div>
						
						<div class="col-6 mb-3">
							<div style="height: 200px; border: solid black 1px;">조직도 보기</div>
						</div>
					
										
						<div class="col-6 mb-3">
							<div style="height: 200px; border: solid black 1px;">지정 결재자 목록</div>
						</div>
						
						<div class="col-6 mb-3">
							<div style="height: 200px; border: solid black 1px;">지정 참조자 목록</div>
						</div>
											
					
						<div class="col-6 mb-3">
							<div style="height: 50px; border: solid black 1px;">완료</div>
						</div>
						
						<div class="col-6 mb-3">
							<div style="height: 50px; border: solid black 1px;">돌아가기</div>
						</div>
						
					</div>
					
					<!-- contents 내용 끝 -->

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