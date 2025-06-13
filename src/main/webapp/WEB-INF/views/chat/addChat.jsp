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

					<div class="row h-100 justify-content-center align-items-center">
						<div class="col-6 mt-3">
							<div class="card shadow mb-4" style="min-height: 80vh;">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-primary">채팅방 만들기</h6>
								</div>
								<div class="card-body">
									<label for="inputRoomName">채팅방 이름</label> <input type="text"
										class="form-control" id="inputRoomName">
									<div class="d-flex flex-column">
										<button type="button"
											class=" btn btn-danger mt-5 ml-auto btn btn-primary ml-auto mt-2"
											data-toggle="modal" data-target="#exampleModal" id="diagram">
											대화상태 추가하기</button>

										<div class="card border-left-primary shadow mt-3"
											style="width: 100%; height: 400px;">
											<div class="card-body row" id="people">
												<div class="col-3">
													<div
														class="alert alert-secondary d-flex justify-content-between align-items-center user"
														role="alert" data-user-id="HRtest1">
														<span>test1</span>
														<button class="btn btn-sm delPerson" type="button">x</button>
													</div>
													<!-- <div
														class="alert alert-secondary d-flex justify-content-between align-items-center user"
														role="alert" data-user-id="test2">
														<span>test2</span>
														<button class="btn btn-sm delPerson" type="button">x</button>
													</div> -->
													
												</div>
												<div class="col-3">
													
												</div>

												<div class="col-3"></div>
												<div class="col-3"></div>




											</div>
										</div>
										<button class="btn btn-primary ml-auto mt-4" type="button" id="addRoomBtn">채팅방
											만들기</button>
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
	<script src="/js/chat/add.js"></script>
	<c:import url="/WEB-INF/views/templates/ocModal.jsp"></c:import>
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
</html>