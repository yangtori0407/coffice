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
					<!-- contents 내용 -->
					<h3>${vo.ingredientsName}</h3>
					<h3>${vo.ingredientsStock}</h3>
					<h3>${vo.ingredientsPrice}</h3>
					<table class="table table-striped" style="margin:20px auto;width: 600px;">
						<thead>
							<tr>
								<th>상태</th>
								<th>수량</th>
								<th>등록</th>
								<th>날짜</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="h">
							<c:forEach items="${h.history}" var="item">
							<tr>
								<td>${item.receive?"입고":"출고"}</td>
								<td>${item.number}</td>
								<td>${item.userVO.name}</td>
								<td>${item.registrationDate}</td>			
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