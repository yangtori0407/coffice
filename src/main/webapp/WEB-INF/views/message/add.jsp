<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>COFFICE</title>
<link href="/images/3.png" rel="shortcut icon" type="image/x-icon">
<link
	href="https://cdn.jsdelivr.net/npm/quill@2.0.3/dist/quill.snow.css"
	rel="stylesheet" />
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
					<!-- <form action="./add" method="post" class="d-flex flex-column"> -->
					<div class="d-flex flex-column">
						<span>받는 사람</span>
						<div id="receiverArea" class="d-flex" >
							<input type="email" class="card mb-2 py-1 border-primary p-0"
								name="sender" style="width: 100%;" id="emailInput">
							

						</div>
						<div class="mb-3">
							<button type="button" class="btn btn-primary"
								data-toggle="modal" data-target="#exampleModal" id="diagram">조직도</button>
							<button class="btn btn-info" type="button"
								data-toggle="collapse" data-target="#replyTo"
								aria-expanded="false" aria-controls="collapseExample">
								답장받을 이메일</button>
						</div>
						<div class="collapse mb-2" id="replyTo">
							<div class="card card-body p-0"><input type="email" name="replyEmail"></div>
						</div>
						<label>제목</label> <input type="text"
							class="card mb-4 py-3 border-left-info" name="messageTitle"
							style="width: 100%;"> <label>내용</label>
						<div class="card" style="margin-bottom: 20px;">
							<div id="editor" style="height: 550px;"></div>
							<input type="hidden" id="quill_html" name="messageContents">
						</div>
						<div class="ml-auto">
							<button id="submitBtn" class="btn btn-primary mb-3" type="submit">글
								작성하기</button>
						</div>
					<!-- </form> -->
					</div>
				</div>
			</div>
			<!-- end Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
	</div>
	<!-- End Wrapper -->
	<script src="https://cdn.jsdelivr.net/npm/quill@2.0.3/dist/quill.js"></script>
	<script src="/js/message/messageAdd.js"></script>
	<c:import url="/WEB-INF/views/templates/ocModal.jsp"></c:import>
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>

</body>


</html>