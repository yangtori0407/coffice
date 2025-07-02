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
<script type="module"
	src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule
	src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>

<style>
.senderArea {
	background-color: rgb(183, 204, 248);
	border-radius: 20px;
	padding: 5px 10px;
	color: black;
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
					<sec:authentication property="principal" var="user" />
					<!-- <h2 class="mb-2 text-gray-800">익명게시판</h2> -->
					<div class="d-flex flex-column">

						<div class="card mb-3 py-0 border-left-info">
							<div class="card-body" id="board"
								data-board-num="${detail.messageNum }">${detail.messageTitle }</div>
						</div>

						<div class="d-flex flex-column mb-3 ">
							<div class="d-flex mb-3 ">
								<div class="mr-2" style="padding: 5px 10px 5px 0px;">보낸사람</div>
								<div class="senderArea">${detail.senderName }
									&lt;${detail.email}&gt;</div>
							</div>
							<div class="d-flex ">
								<div class="mr-2" style="padding: 5px 10px 5px 0px;">받는사람</div>
								<c:forEach items="${detail.receiveUsers }" var="u">
									<div class="senderArea mr-2">
										<c:choose>
											<c:when test="${u.name eq null }">${u.userId }</c:when>
											<c:otherwise>${u.deptName } ${u.name }</c:otherwise>
										</c:choose>
									</div>
								</c:forEach>

							</div>
						</div>
						<c:if test="${detail.files.size() > 0 }">

							<div class="card shadow mb-4">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-primary">첨부파일</h6>
								</div>
								<div class="card-body">
									<c:forEach items="${detail.files }" var="l">
										<a href="/message/fileDown?fileNum=${l.fileNum }" class="mr-1">${l.originName }</a>
									</c:forEach>
								</div>
							</div>
						</c:if>


						<div class="card shadow mb-3" style="min-height: 600px;">
							<div class="card-header py-3">
								<h6 class="m-0 font-weight-bold text-primary">작성일:
									${detail.formatted }</h6>
							</div>
							<div class="card-body">${detail.messageContents }</div>
						</div>

						<div class="mb-2 ml-auto">
							<button class="btn btn-danger" id="delBtn" type="button"
								data-message-num="${detail.messageNum }">삭제하기</button>
							<c:if test="${message eq 'receive' }">
								<a href="/message/reply?messageNum=${detail.messageNum }"
									class="btn btn-primary">답장하기</a>

							</c:if>
						</div>
					</div>

				</div>
				<!-- end Content -->
				<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
			</div>
			<!-- End Content Wrapper -->
		</div>
		<!-- End Wrapper -->
		<script src="/js/message/messageDetail.js"></script>
		<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>

</html>