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
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">
				<c:forEach items="${list}" var="l" varStatus="s">
					<c:if test="${s.first}">
						<h3>지점:${l.branchName}</h3>
						<h3>주소:${l.branchAddress}</h3>			
					</c:if>
				</c:forEach>
					<h3>총매출:${total}</h3>
					<table class="table table-striped" style="margin:20px auto;width: 600px;">
						<thead>
							<tr>
								<th>상태</th>
								<th>금액</th>
								<th>수량</th>
								<th>메뉴</th>
								<th>날짜</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="h">
								<c:forEach items="${h.salesVO}" var="item">
									<tr>
										<td style="color:${item.salesType?'blue':'red'};">${item.salesType?"수입":"지출"}</td>
										<td>${item.salesProfit}</td>
										<td>${item.salesQuantity}</td>			
										<td>${item.menuVO.menuName}</td>
										<td>${item.salesDate}</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
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