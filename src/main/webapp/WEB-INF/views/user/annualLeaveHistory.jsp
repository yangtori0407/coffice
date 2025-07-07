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
							
							<table class="table table-bordered table-hover text-center">
							  <thead class="table-primary">
							    <tr>
							      <th>연차 ID</th>
							      <th>연도</th>
							      <th>구분</th>
							      <th>일수</th>
							      <th>부여일</th>
							      <th>사용일</th>
							      <th>휴가 ID</th>
							    </tr>
							  </thead>
							  <tbody>
							    <c:forEach items="${allList}" var="l">
							      <tr>
							        <td>${l.leaveId}</td>
							        <td>${l.leaveYear}</td>
							        <td>
							          <c:choose>
							            <c:when test="${l.type == '정기'}">정기</c:when>
							            <c:when test="${l.type == '월개근'}">월개근</c:when>
							            <c:when test="${l.type == '사용'}">사용</c:when>
							            <c:otherwise>${l.type}</c:otherwise>
							          </c:choose>
							        </td>
							        <td>
							          <c:choose>
							            <c:when test="${not empty l.grantLeave}">${l.grantLeave}</c:when>
							            <c:when test="${not empty l.usedLeave}">${l.usedLeave}</c:when>
							            <c:otherwise>-</c:otherwise>
							          </c:choose>
							        </td>
							        <td>
							          <c:choose>
							            <c:when test="${not empty l.grantDate}">${l.grantDate}</c:when>
							            <c:otherwise>-</c:otherwise>
							          </c:choose>
							        </td>
							        <td>
							          <c:choose>
							            <c:when test="${not empty l.usedDate}">
							              ${fn:replace(l.usedDate, 'T', ' ')}
							            </c:when>
							            <c:otherwise>-</c:otherwise>
							          </c:choose>
							        </td>
							        <td>
							          <c:choose>
							            <c:when test="${not empty l.vacationId}">${l.vacationId}</c:when>
							            <c:otherwise>-</c:otherwise>
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

			<!-- end Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
	</div>
	<!-- End Wrapper -->
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
</html>