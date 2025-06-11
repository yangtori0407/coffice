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
					<div class="row">
						<div class="col-6" >
							<div class="card shadow mb-4" style="min-height: 80vh;">
								<div class="card-header py-3 d-flex">
									<h6 class="m-1 font-weight-bold text-primary">채팅 목록</h6>
									<a href="./addRoom" class="btn btn-danger btn-sm ml-auto">+</a>
								</div>
								<div class="card-body">
									<div class="list-group">
									<c:forEach items="${list }" var="r">
										<button type="button"
											class="list-group-item list-group-item-action">${r.chatRoomName}</button>
									</c:forEach>
								
									</div>
								</div>
							</div>
						</div>
						<div class="col-6">
							<div class="card shadow mb-4 " style="min-height: 80vh;">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-primary">채팅창</h6>
								</div>
								<div class="card-body">The styling for this basic card
									example is created by using default Bootstrap utility classes.
									By using utility classes, the style of the card component can
									be easily modified with no need for any custom CSS!</div>
							</div>
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
	<script scr="/js/chat/main.js"></script>
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
</html>