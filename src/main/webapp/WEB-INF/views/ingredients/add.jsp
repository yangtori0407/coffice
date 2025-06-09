<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<style>
	.input-box {
		width: 100%;
		height: 50px;
		margin: 20px 0;
	}
	.error {
			color: red;
			font-size: 0.9em;
		}
</style>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">
					<div style="width:400px; height:600px; margin: 0 auto;">
						<form:form modelAttribute="ingredientsVO" action="./add" method="post">
							<div class="input-box">
								<form:input type="text" path="ingredientsName" placeholder="이름"/>						
                                 <form:errors path="ingredientsName" cssClass="error"/>
							</div>

							<div class="input-box">
								<form:input type="text" path="ingredientsPrice" placeholder="가격"/>					
							</div>
							
							<button type="submit" class="btn btn-primary">상품등록</button>
						</form:form>
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
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/js/branch/add.js"></script>
</html>