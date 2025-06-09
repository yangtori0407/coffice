<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>COFFICE</title>
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
<script type="module"
	src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule
	src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>

<style>
	.items {
		width: 50%;
		border: solid 1px black;
		margin: 5px 5px;
		display: flex;	
	}

	.items:hover {
		background-color: rgb(182, 207, 199);
	}

	.items:active {
		background-color: rgb(133, 172, 159);
	}

	.forms.selected {
    background-color: rgb(133, 172, 159);
	}

	#id_approvalLine {
		display: flex;		
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

					<!-- contents 내용 -->

					<div class="row" style="height: 1000px;">
					
						<div class="col-6 mb-3">
							<div style="height: 200px; border: solid black 1px;">양식 목록 창
								<c:forEach var="i" items="${forms}">
									<div class="items forms" id="${i.formId}">${i.formId} ${i.name}</div>
								</c:forEach>
							</div>
						</div>
						
						<div class="col-6 mb-3">
							<div style="height: 200px; border: solid black 1px;">양식 미리보기</div>
						</div>
						
						
						<div class="col-6 mb-3" id="id_employees_wrapper">
							<div style="height: 200px; border: solid black 1px;">사원 목록 창
								<c:forEach var="i" items="${users}">
									<div class="items employees" id="${i.userId}" data-position="${i.position}">${i.name}</div>
								</c:forEach>
							</div>
						</div>
						
						<div class="col-6 mb-3">
							<div style="height: 200px; border: solid black 1px;">조직도 보기</div>
						</div>
					
										
						<div class="col-6 mb-3" id="id_approvalLine_wrapper">
							<div id="id_approvalLine" style="height: 200px; border: solid black 1px;">지정 결재자 목록</div>
						</div>
						
						<div class="col-6 mb-3" id="id_referenceLine_wrapper">
							<div id="id_referenceLine" style="height: 200px; border: solid black 1px;"></div>
						</div>
											
					
						<div class="col-6 mb-3">
							<div style="height: 50px; border: solid black 1px;">돌아가기</div>
						</div>
						
						<div class="col-6 mb-3">
							<div id="id_done_button" style="height: 50px; border: solid black 1px;">완료하기</div>
							
							<!-- 세팅 완료 시 넘길 데이터 Form -->
							<form id="makeForm" action="/document/make" method="post">
							  <input type="hidden" name="formId" id="formId">
							  <input type="hidden" name="approvers" id="approvers">
							  <input type="hidden" name="referrers" id="referrers">
							</form>
						</div>
						
					</div>
					
					<!-- contents 내용 끝 -->

				</div>
			</div>
			<!-- end Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
	</div>
	<!-- End Wrapper -->
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
		
	<script src="/js/document/makeSetting.js"></script>
	
</body>
</html>