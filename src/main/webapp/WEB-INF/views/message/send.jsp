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
	<div class="card shadow mb-4">
						<!-- <div class="card-header py-3">
							<h6 class="m-0 font-weight-bold text-primary"></h6>
						</div> -->
						<div class="card-body">
							<div>
								<div id="dataTable_wrapper"
									class="dataTables_wrapper dt-bootstrap4">
									<div class="row">
										<div class="col-12 mb-4">
											<div
												class="d-flex justify-content-between align-items-center">
												<!-- 검색 폼 -->
												<form method="get"
													class="form-inline d-flex align-items-center">
													<select class="custom-select mr-2" name="kind">
														<option value="k1">제목</option>
														<option value="k2">본문</option>
														<option value="k3">제목+본문</option>
													</select> <input type="search" name="search"
														class="form-control form-control-sm mr-2"
														placeholder="검색어를 입력하세요" style="max-width: 200px;">
													<button type="submit" class="btn btn-info ">검색</button>
												</form>

												<!-- 추가 버튼 -->
												<a href="./add" class="btn btn-danger">작성하기</a>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<table class="table table-bordered dataTable" id="dataTable"
												width="100%" cellspacing="0" role="grid"
												aria-describedby="dataTable_info" style="width: 100%;">
												<thead>
													<tr role="row">
														<th class="sorting sorting_asc" tabindex="0"
															aria-controls="dataTable" rowspan="1" colspan="1"
															aria-sort="ascending"
															aria-label="Name: activate to sort column descending"
															style="width: 100.788px;">받는 사람</th>
														<th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Position: activate to sort column ascending"
															style="width: 450px;">제목</th>
														<th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Office: activate to sort column ascending"
															style="width: 116.788px;">날짜</th>
															<th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Office: activate to sort column ascending"
															style="width: 116.788px;">수신 확인</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${list }" var="m">
														<tr>
															<td>
																
															 	<c:choose>
															 		<c:when test="${m.receiveUsers[0].name eq null }">
															 			${m.receiveUsers[0].userId }
															 		</c:when>
															 		<c:otherwise>
															 			${m.receiveUsers[0].deptName } ${m.receiveUsers[0].name }
															 		</c:otherwise>
															 	</c:choose>
															 	<c:if test="${m.receiveUsers.size() > 1 }">
															 		외 ${m.receiveUsers.size() - 1 }
															 	</c:if>
															</td>
															<td><a class="boardA" href="./send/detail?messageNum=${m.messageNum }">${m.messageTitle}</a></td>
															<td>${m.formatted}</td>
															<td>
															<button class="btn btn-sm btn-primary">수신확인</button>
															</td>
														
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12 col-md-5"></div>
										<div class="col-sm-12 col-md-7">
											<div class="dataTables_paginate paging_simple_numbers"
												id="dataTable_paginate">
												<%-- <ul class="pagination">
													<li class="paginate_button page-item previous"
														id="dataTable_previous"><a
														href="./list?nowPage=${pager.start-1 }&search=${pager.search}&kind=${pager.kind}"
														aria-controls="dataTable" data-dt-idx="0" tabindex="0"
														class="page-link">Previous</a></li>
													<c:forEach begin="${pager.start }" end="${pager.end }"
														var="i">
														<li
															class="paginate_button page-item ${pager.nowPage == i ? 'active' : '' }"><a
															href="./list?nowPage=${i }&search=${pager.search}&kind=${pager.kind}"
															aria-controls="dataTable" data-dt-idx="1" tabindex="0"
															class="page-link">${i}</a></li>
													</c:forEach>


													<li class="paginate_button page-item next ${pager.endCheck == false ? '' : 'disabled' }
														id="dataTable_next"><a
														href="./list?nowPage=${pager.end+1 }&search=${pager.search}&kind=${pager.kind}"
														aria-controls="dataTable" data-dt-idx="7" tabindex="0"
														class="page-link">Next</a></li>
												</ul> --%>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
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
</html>