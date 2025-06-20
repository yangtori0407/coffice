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
  table {
    width: 100%;
    border-collapse: collapse;
    table-layout: fixed;
  }

  th, td {
    border: 1px solid #ccc;
    text-align: center;
    padding: 8px;
    word-wrap: break-word;
  }

  thead th {
    background-color: #f2f2f2;
  }

  th:nth-child(1), td:nth-child(1) { width: 10%; }  /* 문서번호 */
  th:nth-child(2), td:nth-child(2) { width: 12%; }  /* 양식종류 */
  th:nth-child(3), td:nth-child(3) { width: 25%; }  /* 제목 */
  th:nth-child(4), td:nth-child(4) { width: 13%; }  /* 작성자 */
  th:nth-child(5), td:nth-child(5) { width: 15%; }  /* 작성시간 */
  th:nth-child(6), td:nth-child(6) { width: 12%; }  /* 진행단계 */
  th:nth-child(7), td:nth-child(7) { width: 13%; }  /* 문서상태 */
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
					document List 페이지
					<table >
						<thead>						
							<tr role="row">
								<th>문서번호</th>
								<th>양식종류</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>진행단계</th>
								<th>문서상태</th>								
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${docuList}" var="i">
								<tr>
									<td>${i.documentId }</td>
									<td>${i.formId}/${i.formName }</td>
									<td><a href="../detail?documentId=${i.documentId}">${i.title}</a></td>
									<td>${i.writerId } / ${i.writerName } / ${i.writerPosition }</td>
									<td>${i.writerTime }</td>
									<!-- currentStep을 1만큼 낮춰서 출력 -->
									<td>${i.currentStep -1} / ${i.approvalLineVOs.size() }</td>
									<td>${i.status }</td>
								</tr>
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