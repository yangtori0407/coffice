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

<style>
.dropdown-toggle::after {
	display: none !important;
}

input {
	box-shadow: none;
	outline: none;
}
</style>

</head>

<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">

					<!-- contents 내용 -->
					<sec:authentication property="principal" var="user" />
					<!-- <h2 class="mb-2 text-gray-800">익명게시판</h2> -->
					<div class="d-flex flex-column">

						<div class="card mb-4 py-2 border-left-info">
							<div class="card-body" id="board" data-board-num="${detail.boardNum }">${detail.boardTitle }</div>
						</div>
						<c:if test="${detail.userId == user.userId }">
							<div class="mb-2 ml-auto">
								<button class="btn btn-danger" id="delBtn" type="button"
									data-board-num="${detail.boardNum }">삭제하기</button>
								<a href="./update?boardNum=${detail.boardNum }"
									class="btn btn-primary">수정하기</a>
							</div>
						</c:if>
						<div class="card shadow mb-3" style="min-height: 600px;">
							<div class="card-header py-3">
								<h6 class="m-0 font-weight-bold text-primary">작성일:
									${detail.boardDate }</h6>
								<%-- <h6 class="m-0 font-weight-bold text-primary">조회수:
									${detail.boardHit }</h6> --%>
							</div>
							<div class="card-body">${detail.boardContents }</div>
						</div>
						<div>
							<div>
								<div class="card mb-4">
									<div class="card-body">
										<div class="row">
											<div class="col-11 pr-1">
												<input type="text" id="contents" name="commentContents"
													class="form-control border-0" placeholder="댓글을 입력하세요"
													style="box-shadow: none; outline: none;">
											</div>
											<div class="col-1 pl-1">
												<button id="comBtn" class="btn btn-primary w-100"
													type="button">입력</button>
											</div>
										</div>
									</div>
								</div>

							</div>
							<div id="comArea">
								<c:forEach items="${detail.comments }" var="com"
									varStatus="status">
									<c:choose>

										<c:when test="${com.deleteStatus eq '1'}">
											<div class="card mb-2 py-1 border-left-warning">
												<div class="row w-100 m-0">
													<div class="col-10 card-body">삭제된 댓글입니다.</div>
													<div
														class="col-2 d-flex align-items-start justify-content-end mt-1">
														<div class="d-flex flex-column mr-4">
															<button class="btn btn-primary reply"
																data-comment-num="${com.commentNum}" type="button"
																data-toggle="collapse"
																data-target="#collapse${com.commentNum }"
																aria-expanded="false" aria-controls="collapseExample">
																답글</button>

														</div>
													</div>
												</div>
											</div>


											<div class="collapse" id="collapse${com.commentNum }"></div>
										</c:when>
										<c:otherwise>

											<div class="card mb-2 py-1 border-left-warning">
												<div class="row w-100 m-0">
													<div class="col-10 card-body">
														<div style="font-size: 13px; margin-bottom: 10px;">
															${com.formatted}</div>
														<div>${com.commentContents}</div>
													</div>
													<div
														class="col-2 d-flex align-items-start justify-content-end mt-1">
														<div class="d-flex flex-column mr-4">
															<button class="btn btn-primary reply"
																data-comment-num="${com.commentNum}" type="button"
																data-toggle="collapse"
																data-target="#collapse${com.commentNum }"
																aria-expanded="false" aria-controls="collapseExample">
																답글</button>

														</div>
														<c:if test="${com.userId == user.userId }">
															<div class="dropdown">
																<button
																	class="btn dropdown-toggle p-0 border-0 bg-transparent"
																	type="button" data-toggle="dropdown"
																	aria-expanded="false">
																	<ion-icon name="ellipsis-vertical-outline"></ion-icon>
																</button>
																<div class="dropdown-menu dropdown-menu-right">
																	<button class="dropdown-item commentUpBtn"
																		data-com-num="${com.commentNum }">수정</button>
																	<button class="dropdown-item commentDelBtn"
																		data-com-num="${com.commentNum }">삭제</button>
																</div>
															</div>
														</c:if>
													</div>
												</div>
											</div>


											<div class="collapse" id="collapse${com.commentNum }"></div>
										</c:otherwise>
									</c:choose>



								</c:forEach>

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
		<script src="/js/posts/boardDetail.js"></script>
		<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>

</html>