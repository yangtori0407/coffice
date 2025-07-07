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
					<div class="mb-5">

						<div class="card shadow" style="min-height: 80vh;">
							<div class="card-header py-3 d-flex">
								<h6 class="m-1 font-weight-bold text-primary">채팅 목록</h6>
								<a href="./addChat" class="btn btn-primary btn-sm ml-auto">+</a>
							</div>
							<div class="card-body"
								style="max-height: 630px; overflow-y: auto;">
								<div class="list-group">
									<c:forEach items="${list }" var="r">
										<a href="./chatRoom?chatRoomNum=${r.chatRoomNum}"
											class="list-group-item list-group-item-action d-flex align-items-center justify-content-between"
											style="height: 80px;"> <span>🌟${r.chatRoomName} <c:choose>
													<c:when test="${r.alarmStatus eq 1 }">
														<ion-icon name="notifications-outline"
															style="vertical-align: middle;"></ion-icon>
													</c:when>
													<c:otherwise>
														<ion-icon name="notifications-off-outline"
															style="vertical-align: middle;"></ion-icon>
													</c:otherwise>
												</c:choose>
										</span> <c:if test="${r.chatAmount ne 0 }">
												<div id="chatAmount"
													style="width: 8%; border: 1px solid red; border-radius: 10px; background-color: red; color: white; text-align: center;">
													${r.chatAmount }</div>
											</c:if>

										</a>
									</c:forEach>


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