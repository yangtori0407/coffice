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

<link rel="stylesheet" type="text/css" href="/css/user/index_employee.css">



</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">

					<!-- contents 내용 -->

					<div class="row" style="height: 1000px;">

						<!-- 파트1 -->
						<!-- 사원정보 부분 -->
						<div class="col-3">
							
							<!-- Background Gradient Utilities -->
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">사원정보 부분</h6>
                                </div>
                                <div class="card-body">
                                    <div class="employee px-3 py-5 text-center">
										<c:choose>
										  <c:when test="${not empty user.saveName}">
										    <img src="/coffice/upload/profile/${user.saveName}" class="circle-img" alt="Profile Image">
										  </c:when>
										  <c:otherwise>
										    <img src="/images/coffice.png" class="circle-img" alt="Default Profile">
										  </c:otherwise>
										</c:choose>
										<div class="employee-name">${user.name}</div>
    									<div class="employee-info">${user.deptName} / ${user.position}</div>
									</div>
									<div class="now text-center">날짜 | 시간</div>
									<div style="text-align: center;">
                                    	<button type="button" class="btn btn-outline-primary custom-btn">출근</button>
                                    </div>
                                    <div class="text-start">
                                    	<div class="mb-7">근무정보</div>
                                    	<div class="work-info">
										  <div class="label">출근</div>
										  <div class="time">09:00</div>
										  <div class="label">퇴근</div>
										  <div class="time">18:00</div>
										</div>
                                    </div>
                                    <!-- <div class="px-3 py-5 bg-gradient-success text-white">
                                    	<div>휴가(총 15일)</div>
                                    	<div>사용 - 1일</div>
                                    	<div>잔여 - 14일</div>
                                    </div>
                                    <div class="px-3 py-5 bg-gradient-info text-white">.bg-gradient-info</div>
                                    <div class="px-3 py-5 bg-gradient-warning text-white">.bg-gradient-warning</div>
                                    <div class="px-3 py-5 bg-gradient-danger text-white">.bg-gradient-danger</div> -->
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
		                                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
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
		                                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
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
		                                            <div class="text-xs font-weight-bold text-info text-uppercase mb-1">결
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
		                                            <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
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
							
							<!-- 게시판 부분 -->
							<div class="row" style="height: 700px;">
								<div class="col" style="height: 700px;">
									<!-- Roitation Utilities -->
		                            <div class="card">
		                                <div class="card-header py-3">
		                                    <h6 class="m-0 font-weight-bold text-primary">게시판</h6>
		                                </div>
		                                <div class="card-body text-center">
		                                    <div class="bg-primary text-white p-3 rotate-15 d-inline-block my-4">.rotate-15
		                                    </div>
		                                    <hr>
		                                    <div class="bg-primary text-white p-3 rotate-n-15 d-inline-block my-4">.rotate-n-15
		                                    </div>
		                                </div>
		                            </div>
								</div>
							</div>
						</div>
						
						<!-- 파트3 -->
						<div class="col-3">
							<!-- 일정부분 -->
							<div class="row" style="height: 500px;">
								<div class="col" style="min-width: 300px;">
									<!-- Custom Text Color Utilities -->
									<div class="card shadow mb-4">
										<div class="card-header py-3">
											<h6 class="m-0 font-weight-bold text-primary">일정부분</h6>
										</div>
										<div class="card-body" style="min-height: 400px;">
											<div id="schedule"></div>
										</div>
									</div>
								</div>
							</div>
							<!-- 달력부분 -->
							<div class="row" style="min-height: 500px;">
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


					</div><!-- contents 내용 끝 -->

				</div>
			</div>
			<!-- end Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
	</div>
	<!-- End Wrapper -->
	<input type="hidden" value="${apiKey}" id="apiKey">
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
	<script src='/fullcalendar/dist/index.global.js'></script>
	<script src="/js/calendar/homeCalendar.js"></script>
</body>
</html>