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

<style>
.dropdown-toggle::after {
	display: none !important;
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
					<div>

						<div class="card mb-4 py-3 border-left-info">
							<div class="card-body">${detail.boardTitle }</div>
						</div>

						<div class="mb-2">
							<button class="btn btn-danger" id="delBtn" type="button"
								data-board-num="${detail.boardNum }">삭제하기</button>
							<a href="./update?boardNum=${detail.boardNum }"
								class="btn btn-primary">수정하기</a>
						</div>
						<div class="card shadow mb-3" style="min-height: 600px;">
							<div class="card-header py-3">
								<h6 class="m-0 font-weight-bold text-primary">작성일:
									${detail.boardDate }</h6>
								<h6 class="m-0 font-weight-bold text-primary">조회수:
									${detail.boardHit }</h6>
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
												<button id="comBtn" class="btn btn-success w-100"
													type="button">입력</button>
											</div>
										</div>
									</div>
								</div>

							</div>
							<div id="comArea">
								<c:forEach items="${detail.comments }" var="com"
									varStatus="status">
									<div class="card mb-2 py-1 border-left-warning">
										<div class="row w-100 m-0">
											<div class="col-10 card-body">
												<div style="font-size: 13px; margin-bottom: 10px;">${com.formatted}</div>
												<div>${com.commentContents}</div>
											</div>
											<div
												class="col-2 d-flex align-items-start justify-content-end mt-1">
												<button class="btn btn-info mr-3 reply">답글</button>
												<div class="dropdown">
													<button
														class="btn dropdown-toggle p-0 border-0 bg-transparent"
														type="button" data-toggle="dropdown" aria-expanded="false">
														<ion-icon name="ellipsis-vertical-outline"></ion-icon>
													</button>
													<div class="dropdown-menu dropdown-menu-right">
														<a class="dropdown-item" href="#">수정</a> <a
															class="dropdown-item" href="#">삭제</a>
													</div>
												</div>
											</div>
										</div>
									</div>

		
									

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