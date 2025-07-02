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
											          <c:when test="${req.status eq '승인'}">
											            승인 완료
											          </c:when>
													  <c:when test="${req.status eq '거절'}">
											            승인 거절
											          </c:when>
											          <c:otherwise>
											            승인 대기
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
											<th>신청번호</th>
											<th>처리일자</th>
											<th>시작일</th>
											<th>종료일</th>
											<th>휴가 종류</th>
											<th>비고</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${approvalList}" var="app">
											<tr>
												<td>${app.vacationId}</td>
												<td>
													<c:choose>
														<c:when test="${app.status eq '대기'}">
															
														</c:when>
														<c:otherwise>
															${fn:replace(app.editTime, 'T', ' ')}
														</c:otherwise>
													</c:choose>
												</td>
												<td>${fn:replace(app.startTime, 'T', ' ')}</td>
												<td>${fn:replace(app.endTime, 'T', ' ')}</td>
												<td>${app.type}</td>
												<td>
													<c:choose>
											          <c:when test="${app.status eq '승인'}">
											            승인 완료
											          </c:when>
													  <c:when test="${app.status eq '거절'}">
											            승인 거절
											          </c:when>
											          <c:otherwise>
											            승인 대기
											          </c:otherwise>
											        </c:choose>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						
						</div>
					</div>

				</div>

			</div>

			<!-- vacationDetailModal -->
			<div class="modal fade" id="vacationDetailModal" tabindex="-1" aria-labelledby="vacationDetailModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close" name="goMypage">
								<span aria-hidden="true"><ion-icon name="open-outline"></ion-icon></span>
							</button>
						</div>
						<div class="modal-body" style="height: 500px;">

							<div class="card mb-3">
								<div class="card-header">기본 정보</div>
								<input type="hidden" id="vid">
								<div class="card-body">
									<p id="vacationType"></p>
									<p id="applier"></p>
									<p id="insertTime"></p>
									<p id="period"></p>
								</div>
							</div>

							<hr>

							<div class="card">
								<div class="card-body">
									<p id="status"></p>
									<p id="accepter"></p>
									<p id="acceptTime"></p>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-dismiss="modal" id="reject">승인 거절</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal" id="undo">목록으로</button>
							<button type="button" class="btn btn-primary" id="updateVacation" data-dismiss="modal"></button>
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
	<script type="text/javascript" src="/js/user/history.js"></script>
</body>
</html>