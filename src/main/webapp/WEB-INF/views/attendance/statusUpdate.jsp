<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>COFFICE</title>
<link href="/images/3.png" rel="shortcut icon" type="image/x-icon">
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<link rel="stylesheet" type="text/css" href="/css/user/register.css">
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">
					<div class="card shadow mb-4">
						
						<div class="card-body">
							<div>
								<div id="dataTable_wrapper"
									class="dataTables_wrapper dt-bootstrap4">
									
									<div class="row">
										<div class="col-sm-12">
											<table class="table table-bordered dataTable" id="dataTable"
												width="100%" cellspacing="0" role="grid"
												aria-describedby="dataTable_info" style="width: 100%;">
												<h4>님의 근태현황</h4>
												<thead>
													<tr role="row">
														<th class="sorting sorting_asc" tabindex="0"
															aria-controls="dataTable" rowspan="1" colspan="1"
															aria-sort="ascending"
															aria-label="Name: activate to sort column descending"
															style="width: 100px;">날짜</th>
														<th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Position: activate to sort column ascending"
															style="width: 100px;">출근 시간</th>
														<th class="sorting sorting_asc" tabindex="0"
															aria-controls="dataTable" rowspan="1" colspan="1"
															aria-sort="ascending"
															aria-label="Name: activate to sort column descending"
															style="width: 120px;">퇴근 시간</th>
														<th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Office: activate to sort column ascending"
															style="width: 100px;">근태 상태</th>
														
														<th class="sorting" tabindex="0" aria-controls="dataTable"
														rowspan="1" colspan="1"
														aria-label="Age: activate to sort column ascending"
														style="width: 80px;"></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${employeeList}" var="e">
														<tr>
															<td>${e.attendanceDate}</td>
															<td>${e.startTime}</td>
															<td>${e.endTime}</td>
															<td>${e.status}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
									<div class="row">
									  <div class="col-sm-12">
									    <div class="dataTables_paginate paging_simple_numbers text-center" id="dataTable_paginate">
									      <ul class="pagination justify-content-center">
									        <!-- 이전 버튼 -->
									        <li class="paginate_button page-item previous" id="dataTable_previous">
									          <a href="./list?nowPage=${pager.start-1 }&search=${pager.search}&kind=${pager.kind}" class="page-link">Previous</a>
									        </li>
									
									        <!-- 페이지 숫자 -->
									        <c:forEach begin="${pager.start}" end="${pager.end}" var="i">
									          <li class="paginate_button page-item ${pager.nowPage == i ? 'active' : ''}">
									            <a href="./list?nowPage=${i}&search=${pager.search}&kind=${pager.kind}" class="page-link">${i}</a>
									          </li>
									        </c:forEach>
									
									        <!-- 다음 버튼 -->
									        <li class="paginate_button page-item next ${pager.endCheck == false ? '' : 'disabled'}" id="dataTable_next">
									          <a href="./list?nowPage=${pager.end+1}&search=${pager.search}&kind=${pager.kind}" class="page-link">Next</a>
									        </li>
									      </ul>
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