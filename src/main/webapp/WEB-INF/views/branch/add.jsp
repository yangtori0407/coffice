<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${appkey}&libraries=services"></script>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">
					<form action="./add" method="post">
						<div class="col-md-9">
							<input type="text" name="branchName" placeholder="지점이름">						
						</div>
						<div class="col-md-9">
							<input type="text" name="branchPostcode" id="branchPostcode" placeholder="지점우편번호" readonly>
							<input type="button" onclick="daumPostcode()" value="우편번호찾기">
						</div>
						<div class="col-md-9">
							<input type="text" name="branchAddress" id="branchAddress" placeholder="지점주소" readonly>					
						</div>
						
						<button type="submit" class="btn btn-primary">지점등록</button>
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
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/js/branch/add.js"></script>
</html>