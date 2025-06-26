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
<script src= "https://cdn.jsdelivr.net/npm/chart.js"></script>

<link rel="stylesheet" type="text/css" href="/css/user/index_employee.css">
<link rel="stylesheet" type="text/css" href="/css/gpt.css">
<link rel="stylesheet" type="text/css" href="/css/mainSchedule.css">

<c:if test="${not empty msg}">
	<script>
		alert("${msg}");
	</script>
</c:if>

</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">

					<!-- contents 내용 -->

					<div class="row" style="height: 1050px;">

						<!-- 파트1 -->
						<!-- 사원정보 부분 -->
						<div class="col-3">

							<!-- Background Gradient Utilities -->
							<div class="card shadow mb-4" style="height: 530px;">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-primary">사원정보 부분</h6>
								</div>
								<div class="card-body">
									<div class="employee px-3 py-5 text-center">
										<c:choose>
											<c:when test="${not empty user.saveName}">
												<img src="/coffice/upload/profile/${user.saveName}"
													class="circle-img" alt="Profile Image">
											</c:when>
											<c:otherwise>
												<img src="/images/coffice.png" class="circle-img"
													alt="Default Profile">
											</c:otherwise>
										</c:choose>
										<div class="employee-name">${user.name}</div>
										<div class="employee-info">${user.deptName} / ${user.position}</div>
									</div>
									<div class="now">
										<span id="currentDateTime"></span>
									</div>
									<div style="text-align: center;">
										<form action="/attendance/check-in" method="post">
										  <button type="button" class="btn btn-outline-primary checkInBtn">출근</button>
										</form>
										
										<form action="/attendance/check-out" method="post">
										  <button type="button" class="btn btn-outline-danger checkOutBtn" style="display:none;">퇴근</button>
										</form>
										
									</div>
									<div class="text-start">
										<div class="mb-7">근무정보</div>
										<div class="work-info">
										  <div class="item">
										    <div class="label">출근 |</div>
										    <div class="time"><span id="startTime">--</span></div>
										  </div>
										  <div class="item">
										    <div class="label">퇴근 |</div>
										    <div class="time"><span id="endTime">--</span></div>
										  </div>
										  
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<!-- Roitation Utilities -->
									<div class="card mt-3" style="">
										<div
											class="card-header py-3 d-flex align-items-center justify-content-between">
											<h6 class="m-0 font-weight-bold text-primary">공지사항</h6>
											<a href="/notice/list" class="btn btn-sm">+</a>
										</div>
										<div class="card-body text-center p-0" >
											<table class="table table-striped mb-0">
												<thead>
													<tr>
														
														<th scope="col">제목</th>
														<th scope="col">날짜</th>

													</tr>
												</thead>
												<tbody>
													<c:forEach items="${list }" var="l">
														<tr>
															<td ><a
																href="/notice/detail?noticeNum=${l.noticeNum }">${l.noticeTitle }</a></td>
															<td ><a
																href="/notice/detail?noticeNum=${l.noticeNum }">${l.formatted }</a></td>

														</tr>
													</c:forEach>

												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>

						</div>

						<!-- 파트2 -->
						<div class="col-6">

							<!-- 전자결재 부분 -->
							<div class="row" style="height: 300px;">

								<!-- Earnings (Monthly) Card Example -->
								<div class="col-xl-3 col-md-6 mb-4">
									<div class="card border-left-primary shadow h-100 py-2">
										<div class="card-body">
											<div class="row no-gutters align-items-center">
												<div class="col mr-2">
													<div
														class="text-xs font-weight-bold text-primary text-uppercase mb-1">
														Earnings (Monthly)</div>
													<div class="h5 mb-0 font-weight-bold text-gray-800">전</div>
												</div>
												<div class="col-auto">
													<i class="fas fa-calendar fa-2x text-gray-300"></i>
												</div>
											</div>
										</div>
									</div>
								</div>

								<!-- Earnings (Monthly) Card Example -->
								<div class="col-xl-3 col-md-6 mb-4">
									<div class="card border-left-success shadow h-100 py-2">
										<div class="card-body">
											<div class="row no-gutters align-items-center">
												<div class="col mr-2">
													<div
														class="text-xs font-weight-bold text-success text-uppercase mb-1">
														자</div>
													<div class="h5 mb-0 font-weight-bold text-gray-800">$215,000</div>
												</div>
												<div class="col-auto">
													<i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
												</div>
											</div>
										</div>
									</div>
								</div>

								<!-- Earnings (Monthly) Card Example -->
								<div class="col-xl-3 col-md-6 mb-4">
									<div class="card border-left-info shadow h-100 py-2">
										<div class="card-body">
											<div class="row no-gutters align-items-center">
												<div class="col mr-2">
													<div
														class="text-xs font-weight-bold text-info text-uppercase mb-1">결
													</div>
													<div class="row no-gutters align-items-center">
														<div class="col-auto">
															<div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">50%</div>
														</div>
														<div class="col">
															<div class="progress progress-sm mr-2">
																<div class="progress-bar bg-info" role="progressbar"
																	style="width: 50%" aria-valuenow="50" aria-valuemin="0"
																	aria-valuemax="100"></div>
															</div>
														</div>
													</div>
												</div>
												<div class="col-auto">
													<i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
												</div>
											</div>
										</div>
									</div>
								</div>

								<!-- Pending Requests Card Example -->
								<div class="col-xl-3 col-md-6 mb-4">
									<div class="card border-left-warning shadow h-100 py-2">
										<div class="card-body">
											<div class="row no-gutters align-items-center">
												<div class="col mr-2">
													<div
														class="text-xs font-weight-bold text-warning text-uppercase mb-1">
														재</div>
													<div class="h5 mb-0 font-weight-bold text-gray-800">18</div>
												</div>
												<div class="col-auto">
													<i class="fas fa-comments fa-2x text-gray-300"></i>
												</div>
											</div>
										</div>
									</div>
								</div>

							</div>

							<!-- 그래프 부분 -->
							<div class="row" style="height: 700px;">
								<div class="col" style="height: 700px;">
									<!-- Roitation Utilities -->
									<div class="card">
										<div
											class="card-header py-3 d-flex align-items-center justify-content-between">
											<h6 class="m-0 font-weight-bold text-primary">지점별매출(등록지점수:${registerBranch})</h6>
										</div>
										<div class="card-body text-center p-0" style="height: 100%;">
											<canvas id="salesChart"  height="600" width="800"></canvas>
										</div>
									</div>
								</div>
							</div>
						</div>

						<!-- 파트3 -->
						<div class="col-3">
							<!-- 일정부분 -->
							<div class="row" style="height: 400px;">
								<div class="col" style="min-width: 300px;">
									<!-- Custom Text Color Utilities -->
									<div class="card shadow mb-4">
										<div class="card-header py-3">
											<h6 class="m-0 font-weight-bold text-primary">일정부분</h6>
										</div>
										<div class="card-body" style="min-height: 300px;">
											<div id="schedule"></div>
										</div>
									</div>
								</div>
							</div>
							<!-- 달력부분 -->
							<div class="row" style="min-height: 600px;">
								<!-- Custom Text Color Utilities -->
								<div class="col" style="min-width: 300px;">
									<div class="card shadow mb-4">
										<div class="card-header py-3">
											<h6 class="m-0 font-weight-bold text-primary">달력부분</h6>
										</div>
										<div class="card-body">
											<div id="calendar"></div>
										</div>
									</div>
								</div>
                
							</div>
						</div>


					</div>
					<!-- contents 내용 끝 -->
						<!-- gpt  -->
					<div style="position: fixed; bottom: 80px; right: 20px; z-index: 1050;">
						<a href="#" class="gpt-btn" data-toggle="modal" data-target="#getGpt">
							<i class="fas fa-robot mr-2"></i>CofficeBot
						</a>
					</div>
				</div>
			</div>
			<!-- end Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
		 <c:import url="/WEB-INF/views/templates/ocModal.jsp"></c:import>
		 <!--gpt 모달-->
		<div class="modal fade" id="getGpt" tabindex="-1" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" style="max-width: 400px;">
		    <div class="modal-content gpt-modal-content">
		      <div class="modal-header gpt-modal-header">
		        <h5 class="modal-title"><i class="fas fa-robot mr-2"></i>CofficeBot</h5>
				  <ion-icon id="infoIcon" 
				  			name="information-circle-outline"
				  			data-container="body" 
				  			data-toggle="popover" 
				  			data-placement="top"
				  			 data-html="true"
				  			data-content="키워드: 메뉴,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;식자재,
							  			<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;총매출,
							  			<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;지점별매출"></ion-icon>
		        <button class="close text-white" type="button" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
			  <div class="modal-body gpt-modal-body">
			    <div id="chatBox" style="max-height:300px; overflow-y:auto; background:#252525; padding:10px; border-radius:10px;">
			      <!-- 대화 내용 쌓임 -->
			    </div>
			    <textarea id="gptInput" class="form-control gpt-textarea mt-2" rows="2" placeholder="메시지를 입력해주세요.."></textarea>
			    <button id="sendGptBtn" class="btn btn-primary btn-block mt-2">전송</button>
			  </div>
		    </div>
		  </div>
		</div>
	</div>
	<!-- End Wrapper -->
	<input type="hidden" value="${apiKey}" id="apiKey">
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
	<script src='/fullcalendar/dist/index.global.js'></script>
	<script src="/js/calendar/homeCalendar.js"></script>
	<script src="/js/user/attendance.js"></script>
	<script src="/js/gpt/description.js"></script>
</body>
<script type="text/javascript">
$(function () {
    $('#infoIcon').popover();
  });
const label = [
	<c:forEach items="${chart}" var="c" varStatus="s">
		"${c.branchName}"
		<c:if test="${!s.last}">,</c:if>
	</c:forEach>	
];
const datas = [
	<c:forEach items="${chart}" var="c" varStatus="s">
		"${c.totalSale}"
		<c:if test="${!s.last}">,</c:if>
	</c:forEach>	
];

const data = {
		  labels: label,
		  datasets: [{
		    label: 'branchSale',
		    data: datas,
		    backgroundColor: [
		      'rgb(255, 99, 4)',
		      'rgb(54, 162, 235)',
		      'rgb(255, 205, 86)',
		      'rgb(186,234,31)',
		      'rgb(31,75,234)',
		    ],
		    hoverOffset: 4
		  }]
		};
	const salesChart = document.getElementById("salesChart");
	new Chart (salesChart,{
		type: 'doughnut',
		  data: data,
	})
</script>
</html>