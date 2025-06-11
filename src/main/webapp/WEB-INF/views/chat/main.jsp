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
					<div
						class="row h-100 justify-content-center align-items-center mb-4">
						<div class="col-6 mt-3">
							<div class="card shadow" style="min-height: 80vh;">
								<div class="card-header py-3 d-flex">
									<h6 class="m-1 font-weight-bold text-primary">채팅 목록</h6>
									<a href="./addChat" class="btn btn-danger btn-sm ml-auto">+</a>
								</div>
								<div class="card-body"
									style="max-height: 630px; overflow-y: auto;">
									<div class="list-group">
										<c:forEach items="${list }" var="r">
											<a href="./room?chatRoomNum=${r.chatRoomNum}"
												class="list-group-item list-group-item-action d-flex align-items-center" style="height:80px;">
												<span>🌟${r.chatRoomName}</span></a>
										</c:forEach>
										<a href="#"
												class="list-group-item list-group-item-action d-flex align-items-center" style="height:80px;">
												<span>테스트</span></a>
												<a href="#"
												class="list-group-item list-group-item-action d-flex align-items-center" style="height:80px;">
												<span>테스트</span></a>
												<a href="#"
												class="list-group-item list-group-item-action d-flex align-items-center" style="height:80px;">
												<span>테스트</span></a>
												<a href="#"
												class="list-group-item list-group-item-action d-flex align-items-center" style="height:80px;">
												<span>테스트</span></a>
												<a href="#"
												class="list-group-item list-group-item-action d-flex align-items-center" style="height:80px;">
												<span>테스트</span></a>
												<a href="#"
												class="list-group-item list-group-item-action d-flex align-items-center" style="height:80px;">
												<span>테스트</span></a>
												<a href="#"
												class="list-group-item list-group-item-action d-flex align-items-center" style="height:80px;">
												<span>테스트</span></a>
												
										 
									</div>
								</div>
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
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
</html>