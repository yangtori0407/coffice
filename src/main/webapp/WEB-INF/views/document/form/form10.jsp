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
					here is form10.jsp
					<br>

					<div>양식</div>
					<div>${formVO.formId}</div>
					<br>
					
					<div>결재선</div>
					<c:forEach var="i" items="${approvers}">
						<div>${i.userId} ${i.name} ${i.position}</div>
					</c:forEach>
					<br>

					<div>참조선</div>
					<c:forEach var="i" items="${referrers}">
						<div>${i.userId} ${i.name} ${i.position}</div>
					</c:forEach>
					<br>		
					
					<form id="writeForm" action="/document/write" method="post">
					  <input type="text" name="formId" id="formId" value="${formVO.formId}" > <br><br>
					  <input type="text" name="writerId" id="writerId" value="${userVO.userId}" > <br><br>
					
					  <input type="text" name="title" id="title" placeholder="제목"> <br><br>
					  <input type="text" name="content" id="content" placeholder="내용"> <br><br>
					  <input type="file" name="attaches" id="attaches"> <br><br>
					  <!-- 처음 문서 작성 시 보낼 수 있는 결재선 정보 -->
					  <!-- 
					  	문서 번호, 사원 번호, 결재 순번
					   -->
					   
					   <!-- 처음 문서 작성 시 보낼 수 있는 참조선 정보 -->
					  <!-- 
					  	문서 번호, 사원 번호
					   -->
					   <button type="submit">작성 완료</button>
					</form>


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