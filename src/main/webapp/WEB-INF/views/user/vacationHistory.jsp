<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
					<!-- Page Heading -->
					<!-- <h1 class="mb-2 text-gray-800">익명게시판</h1> -->

					<!-- DataTales Example -->
					<div class="card shadow mb-4">
						<!-- <div class="card-header py-3">
							<h6 class="m-0 font-weight-bold text-primary"></h6>
						</div> -->
						<div class="card-body" style="min-height: 800px;">
							
							<!-- 휴가 신청 내역 -->
							<div class="mb-5">
								<h5 class="mb-3">휴가 신청 내역</h5>
								<table class="table table-bordered table-hover text-center" id="requestTable" width="100%">
									<thead class="thead-light">
										<tr>
											<th>신청번호</th>
											<th>신청일자</th>
											<th>시작일</th>
											<th>종료일</th>
											<th>휴가 종류</th>
											<th>상태</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${requestList}" var="req">
											<tr>
												<td>${req.vacationId}</td>
												<td>${fn:replace(req.insertTime, 'T', ' ')}</td>
												<td>${fn:replace(req.startTime, 'T', ' ')}</td>
												<td>${fn:replace(req.endTime, 'T', ' ')}</td>
												<td>${req.type}</td>
												<td>
													<c:choose>
											          <c:when test="${req.status}">
											            승인완료
											          </c:when>
											          <c:otherwise>
											            승인대기
											          </c:otherwise>
											        </c:choose>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>

							<!-- 휴가 승인 내역 -->
							<div>
								<h5 class="mb-3">휴가 승인 내역</h5>
								<table class="table table-bordered table-hover text-center" id="approvalTable" width="100%">
									<thead class="thead-light">
										<tr>
											<th>승인일자</th>
											<th>시작일</th>
											<th>종료일</th>
											<th>휴가 종류</th>
											<th>비고</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${approvalList}" var="app">
											<tr>
												<td>${fn:replace(req.editTime, 'T', ' ')}</td>
												<td>${fn:replace(req.startTime, 'T', ' ')}</td>
												<td>${fn:replace(req.endTime, 'T', ' ')}</td>
												<td>${app.type}</td>
												<td>${app.note}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
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