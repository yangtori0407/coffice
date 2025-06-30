<%@page import="java.util.Map"%>
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
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<link rel="stylesheet" type="text/css" href="/css/user/mypage.css">

 <c:if test="${not empty success}">
  <script>
    alert("${success}");
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
	
					<div class="row" style="height: 650px; padding: 0 30px;">
					  
					  <!-- 왼쪽 큰 박스 -->
					  <div class="col-5 mb-3">
					    <div class="card h-100">
					      <div class="card-header py-2 d-flex justify-content-between align-items-center">
					        <h6 class="m-0 font-weight-bold text-primary">나의 정보</h6>
					        <button type="button" class="btn btn-outline-primary change" data-toggle="modal" data-target="#pwCheckModal">수정</button>
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
							<br>
							<div class="employee-info"><ion-icon name="happy-outline"></ion-icon> 사원번호 : ${user.userId}</div>
							<div class="employee-info"><ion-icon name="call-outline"></ion-icon>  전화번호 : ${user.phone}</div>
							<div class="employee-info"><ion-icon name="mail-outline"></ion-icon> 이메일 :  ${user.email}</div>
							<div class="card mt-4">
							  <div class="card-header text-center font-weight-bold">
							    오늘의 명언
							  </div>
							  <div class="card-body text-center">
							    <ion-icon name="sunny-outline" style="font-size: 24px; vertical-align: middle; margin-right: 8px;"></ion-icon>
							    <span style="font-size: 18px;">${quote}</span>
							  </div>
							</div>
							
							
							<div class="modal fade" id="pwCheckModal" tabindex="-1" role="dialog" aria-hidden="true">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
							      
							      <div class="modal-header">
							        <h5 class="modal-title">본인 확인</h5>
							        <button type="button" class="close" data-dismiss="modal">&times;</button>
							      </div>
							      
							      <div class="modal-body">
							        <input type="password" id="checkPassword" class="form-control" placeholder="비밀번호를 입력하세요">
							        <div id="pwError" class="text-danger mt-2" style="display: none;">비밀번호가 일치하지 않습니다.</div>
							      </div>
							      
							      <div class="modal-footer">
							        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
							        <button type="button" class="btn btn-primary" id="confirmPwBtn">확인</button>
							      </div>
							
							    </div>
							  </div>
							</div>
							</div>
					      </div>
					    </div>
					  </div>
					
					  
					  <div class="col-7 d-flex flex-column">
					    
					    <!-- 오른쪽 위 박스 -->
					    <div class="card" style="flex: 1; margin-bottom: 5px;">
							<div class="card-header py-3">
								<h6 class="m-0 font-weight-bold text-primary">연차 및 휴가</h6>
							</div>
							<div class="card-body" style="display: flex; flex-direction: column; justify-content: center; min-height: 200px;">
							<!-- 오른쪽 위 내용 -->
								<div style="display: flex; justify-content: space-around;  border-bottom: 1px solid black; padding-bottom: 10px;">
									<div>사용 연차</div>
									<div>잔여 연차</div>
									<div>부여 연차</div>
								</div>
								<div style="display: flex; justify-content: space-around; margin-top: 10px; font-weight: bold;">
									<div><a href="#">${annualLeaves.USED}</a>일</div>
									<div><a href="#">${annualLeaves.GRANT}</a>일</div>
									<div><a href="#">${annualLeaves.REMAIN}</a>일</div>
								</div>
							</div>
							<div class="card-footer d-flex justify-content-around text-center" style="padding: 12px 0;">
								<div>
									<a href="/events/vacation" style="text-decoration: none; color: inherit;">
									<ion-icon name="list-outline" style="font-size: 24px;"></ion-icon><br/>
									<span style="font-size: 13px;">전체 조회</span>
									</a>
								</div>

								<div>
									<a href="/user/vacationHistory" style="text-decoration: none; color: inherit;">
									<ion-icon name="calendar-outline" style="font-size: 24px;"></ion-icon><br/>
									<span style="font-size: 13px;">신청/승인 내역</span>
									</a>
								</div>

								<div>
									<a href="/user/annualLeaveHistory" style="text-decoration: none; color: inherit;">
									<ion-icon name="document-text-outline" style="font-size: 24px;"></ion-icon><br/>
									<span style="font-size: 13px;">사용 내역</span>
									</a>
								</div>
							</div>
					    </div>
					    
					    
					    <%! 
						public String timeFormat(long totalSeconds) {
						    long h = totalSeconds / 3600;
						    long m = (totalSeconds % 3600) / 60;
						    long s = totalSeconds % 60;
						    return h + "H " + m + "M " + s + "S";
						}
						%>
						
						<c:set var="worked" value="${timeMap.workedSeconds}" />
						<c:set var="remaining" value="${timeMap.remainingSeconds}" />
						<c:set var="overtime" value="${timeMap.overtimeSeconds}" />
						
						<%
						    Map<String, Long> timeMap = (Map<String, Long>) request.getAttribute("timeMap");
						
						    long worked = timeMap != null && timeMap.get("workedSeconds") != null ? timeMap.get("workedSeconds") : 0L;
						    long remaining = timeMap != null && timeMap.get("remainingSeconds") != null ? timeMap.get("remainingSeconds") : 0L;
						    long overtime = timeMap != null && timeMap.get("overtimeSeconds") != null ? timeMap.get("overtimeSeconds") : 0L;
						%>
					    
					    <!-- 오른쪽 아래 박스 -->
					    <div class="card mb-3" style="flex: 1;">
					      <div class="card-header py-3">
					        <h6 class="m-0 font-weight-bold text-primary">나의 이번 주 근태현황</h6>
					      </div>
				
					      <div class="card-body" style="display: flex; min-height: 250px; padding: 20px;">
  
						  <!-- 왼쪽: 근무 정보 -->
							<div style="flex: 1; display: flex; flex-direction: column; justify-content: center; padding-right: 30px;">
							
							  <div style="display: flex; justify-content: space-between; border-bottom: 1px solid #ccc; padding: 15px 0;">
							    <div>근무한 시간</div>
							    <div class="time"><%= timeFormat(worked) %></div>
							  </div>
							
							  <div style="display: flex; justify-content: space-between; border-bottom: 1px solid #ccc; padding: 15px 0;">
							    <div>잔여 시간</div>
							    <div class="time"><%= timeFormat(remaining) %></div>
							  </div>
							
							  <div style="display: flex; justify-content: space-between; padding: 15px 0;">
							    <div>초과 시간</div>
							    <div class="time"><%= timeFormat(overtime) %></div>
							  </div>
							
							</div>

						
						  <!-- 오른쪽: 그래프 -->
						  <div style="flex: 1; display: flex; justify-content: center; align-items: center;">
						    <canvas id="monthlyStatusChart" style="max-width: 100%;"></canvas>
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
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
	<script src="/js/user/mypage.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<script>
	  window.addEventListener('DOMContentLoaded', () => {
	    const ctx = document.getElementById('monthlyStatusChart')?.getContext('2d');
	
	    if (!ctx) {
	      console.error("monthlyStatusChart not found!");
	      return;
	    }
	
	    new Chart(ctx, {
	      type: 'bar',
	      data: {
	        labels: ['정상근무', '조퇴', '결근'],
	        datasets: [{
	          label: '횟수',
	          data: [
	            ${normalCount != null ? normalCount : 0},
	            ${earlyLeaveCount != null ? earlyLeaveCount : 0},
	            ${absentCount != null ? absentCount : 0}
	          ],
	          backgroundColor: ['#007bff', '#ff9800', '#f44336'],
	          borderWidth: 1
	        }]
	      },
	      options: {
	        responsive: true,
	        scales: {
	          y: {
	            beginAtZero: true,
	            ticks: {
	              precision: 0
	            }
	          }
	        },
	        plugins: {
	          legend: {
	            display: false
	          }
	        }
	      }
	    });
	  });
	</script>
	<script src="/js/user/graph.js"></script>
	
	<script src="/js/user/graph.js"></script>
	<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</body>
</html>