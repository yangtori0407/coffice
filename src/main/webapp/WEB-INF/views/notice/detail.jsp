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
					<div>

						<div class="card mb-4 py-3 border-left-danger">
							<div class="card-body">${detail.noticeTitle }</div>
						</div>

						<div class="card shadow mb-3" style="height: 600px;">
							<div class="card-header py-3">
								<h6 class="m-0 font-weight-bold text-primary">작성일:
									${detail.noticeDate }</h6>
								<h6 class="m-0 font-weight-bold text-primary">조회수:
									${detail.noticeHit }</h6>
							</div>
							<div class="card-body">${detail.noticeContents }</div>
						</div>
						<div class="card mb-3 py-1 border-bottom-secondary">
							<div class="card-body">첨부파일</div>
						</div>
						<div class="mb-2">
							<a class="btn btn-danger">삭제하기</a> <a class="btn btn-primary">수정하기</a>
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