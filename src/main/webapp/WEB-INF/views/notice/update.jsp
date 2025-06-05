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
					<form method="post" enctype="multipart/form-data">
						<label>제목</label> <input type="text"
							class="card mb-4 py-3 border-left-danger" name="noticeTitle"
							style="width: 100%;" value="${update.noticeTitle }" id="noticeTitle"> <label>내용</label>
						<div class="card" style="margin-bottom: 20px;">
							<div id="editor" style="height: 550px;"></div>
							<input type="hidden" id="quill_html" name="noticeContents"
								value="<c:out value='${update.noticeContents }' escapeXml='true'/>">
						</div>
						<label>첨부파일</label>
						<div class="d-flex" id="attacheArea">
							<c:forEach items="${update.files }" var="f">
								<div class="d-flex">
									<div class="d-flex justify-content-center align-items-center">${f.originName}</div>
									<button type="button" class="btn attachDelete" data-file-num="${f.fileNum }">X</button>
								</div>
							</c:forEach>
						</div>
						<div class="form-group d-flex" id="fileArea"
							data-file-length="${update.files.size()}" data-notice-num="${update.noticeNum }">

						</div>
						<div>
							<button id="fileAdd" class="btn btn-danger mb-3" type="button">첨부파일
								추가하기</button>
						</div>
						<div>
							<button class="btn btn-primary mb-3" id="finishBtn" type="button">수정완료</button>
						</div>
					</form>
				</div>
			</div>
			<!-- end Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
	</div>
	<!-- End Wrapper -->
	<script src="https://cdn.jsdelivr.net/npm/quill@2.0.3/dist/quill.js"></script>
	<script src="/js/coffice/noticeUpdate.js"></script>
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>


</body>

</html>