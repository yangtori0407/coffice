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
<style>
	.boxs {
		border: 1px solid black;
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
					<div>
					document Detail 페이지
					</div>
					<br>
					
					<div class="boxs"> 문서 정보 및 작성자 정보
						<div>${vo.documentId }</div>
						<div>${vo.formName }</div>
						<div>${vo.title }</div>
						<div>${vo.content }</div>
						<div>${vo.writerId }</div>
						<div>${vo.writerName }</div>
						<div>${vo.writerPosition }</div>
						<div>${vo.writerDept }</div>
						<div>${vo.writerTime }</div>
						<div>${vo.currentStep }</div>
						<div>${vo.status }</div>
					</div>
					<br>
					
					<div class="boxs"> 수정자 정보
						<div>${vo.modifierId }</div>
						<div>${vo.modifierName }</div>
						<div>${vo.modifierPosition }</div>
						<div>${vo.modifierDept }</div>
						<div>${vo.modifierTime }</div>
					</div>
					<br>
					
					<div class="boxs"> 첨부파일
						<c:forEach items="${vo.attachmentVOs}" var="i">
							<div>${i.documentId}</div>
							<div>${i.fileNum}</div>
							<div>${i.originName}</div>
							<div>${i.saveName}</div><br>
						</c:forEach>
					</div>
					<br>
					
					<div class="boxs"> 결재선
						<c:forEach items="${vo.approvalLineVOs}" var="i">
							<div>${i.documentId}</div>
							<div>${i.userId}</div>
							<div>${i.userName}</div>
							<div>${i.userPosition}</div>
							<div>${i.stepOrder}</div>
							<div>${i.status}</div>
							<div>${i.rejectReason}</div>
							<div>${i.handlingTime}</div><br>
						</c:forEach>
					</div>
					<br>
					
					<div class="boxs"> 참조선
						<c:forEach items="${vo.referenceLineVOs}" var="i">
							<div>${i.documentId}</div>
							<div>${i.userId}</div>
							<div>${i.userName}</div>
							<div>${i.userPosition}</div>
							<br>
						</c:forEach>
					</div>
					<br>
					

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