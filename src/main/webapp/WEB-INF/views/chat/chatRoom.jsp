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
					<sec:authentication property="principal" var="user" />
					<!-- contents 내용 -->
					<div class="row h-100 justify-content-center align-items-center"
						style="height: 80vh;">
						<div class="col-5 mt-3">
							<div class="card shadow mb-4">
								<div class="card-header py-3">
									<h6 id="chatInfo" class="m-0 font-weight-bold text-primary"
										data-chat-num="${chatRoomVO.chatRoomNum }"
										data-user-id="${user.userId }">${chatRoomVO.chatRoomName}</h6>
									<!-- 나중에 userid 시큐리티에서 가지고 오기 -->
								</div>
								<div class="card-body d-flex flex-column" style="height: 80vh;">
									<!-- 채팅 메시지 영역 -->
									<div id="chat-box" class="d-flex flex-column"
										style="flex: 1; overflow-y: auto; max-height: 70vh; border: 1px solid #ccc; padding: 10px; border-radius: 1%;">


										<c:forEach items="${contents }" var="con">
											<c:choose>
												<c:when test="${con.sender eq user.userId }">
													<c:choose>
														<c:when test="${con.fileNum ne null }">
															<div class="d-flex justify-content-end mb-2">
																<div class="mr-2 text-muted small align-self-end">${con.formatted}</div>
																<div class="card border-left-secondary"
																	style="max-width: 60%;">
																	<div class="card-body p-2">
																		<a href="./fileDown?fileNum=${con.fileNum}">${con.chatContents }</a>
																	</div>
																</div>
															</div>
														</c:when>
														<c:otherwise>

															<div class="d-flex justify-content-end mb-2">
																<div class="mr-2 text-muted small align-self-end">${con.formatted}</div>
																<div class="card border-left-secondary"
																	style="max-width: 60%;">
																	<div class="card-body p-2">${con.chatContents }</div>
																</div>
															</div>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${con.fileNum ne null }">
															<div class="d-flex flex-column align-items-start mb-2">

																<div class="mb-1 text-primary font-weight-bold small">${con.name }</div>


																<div class="d-flex">
																	<div class="card border-left-warning"
																		style="max-width: 70%;">
																		<div class="card-body p-2">
																			<a href="./fileDown?fileNum=${con.fileNum}">${con.chatContents }</a>
																		</div>
																	</div>
																	<div class="ml-2 text-muted small align-self-end">${con.formatted}</div>
																</div>
															</div>
														</c:when>
														<c:otherwise>

															<div class="d-flex flex-column align-items-start mb-2">

																<div class="mb-1 text-primary font-weight-bold small">${con.name }</div>


																<div class="d-flex">
																	<div class="card border-left-warning"
																		style="max-width: 70%;">
																		<div class="card-body p-2">${con.chatContents }</div>
																	</div>
																	<div class="ml-2 text-muted small align-self-end">${con.formatted}</div>
																</div>
															</div>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>



										</c:forEach>

										<!-- <div class="d-flex justify-content-end mb-2">
											<div class="mr-2 text-muted small align-self-end">10:32</div>
											<div class="card border-left-secondary"
												style="max-width: 60%;">
												<div class="card-body p-2">오른쪽 정렬 잘
													됨!dfksjflsjfklsjdafjaskldfjlajfksjafljlajflkajfldsjklajsfld</div>
											</div>
										</div>
										<div class="d-flex flex-column align-items-start mb-2">
											sender
											<div class="mb-1 text-primary font-weight-bold small">sender</div>

											말풍선 + 시간
											<div class="d-flex">
												<div class="card border-left-warning"
													style="max-width: 70%;">
													<div class="card-body p-2">안녕하세요sfsdfsfsfsefsefasdfafaefafawfafsdafe</div>
												</div>
												<div class="ml-2 text-muted small align-self-end">10:32</div>
											</div>
										</div> -->
									</div>

									<!-- 입력창 영역 -->
									<div class="card mt-2 p-0 border-bottom-success">
										<div class="card-body row align-items-center p-1 mt-1"
											style="min-height: 60px;">
											<div class="col-10">
												<input type="text" class="form-control border-0 shadow-none"
													placeholder="채팅을 입력하세요" id="chat-input"
													style="height: 38px;">
											</div>
											<div
												class="col-2 d-flex flex-column align-items-end flex-shrink-0">
												<button class="btn btn-outline-success btn-sm mb-1 mr-1"
													id="chat-send-btn" style="width: 100%;">입력</button>
											</div>
										</div>
									</div>
									<div>
										<label for="file-upload"
											class="btn btn-outline-secondary btn-sm mt-2">파일 첨부</label> <input
											type="file" id="file-upload" style="display: none;">
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
	<script src="/js/chat/chatStomp.js"></script>
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
</html>