<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	
	<c:if test="${not empty msg}">
		<script>
			alert("${msg}");
		</script>
	</c:if>
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
										<div class="col-12 mb-4">
											<div
												class="d-flex justify-content-between align-items-center">
												<!-- 검색 폼 -->
												<form method="get" action="/employee/list"
													class="form-inline d-flex align-items-center">
													<select class="custom-select mr-2" name="kind">
														<option value="k1">부서별</option>
														<option value="k2">직급별</option>
														<option value="k3">퇴사자</option>
													</select> <input type="search" name="search"
														class="form-control form-control-sm mr-2"
														placeholder="검색어를 입력하세요" value="${search}" style="max-width: 200px;">
													<button type="submit" class="btn btn-secondary ">검색</button>
												</form>
								
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
															style="width: 100px;">프로필 사진</th>
														<th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Position: activate to sort column ascending"
															style="width: 100px;">이름</th>
														<th class="sorting sorting_asc" tabindex="0"
															aria-controls="dataTable" rowspan="1" colspan="1"
															aria-sort="ascending"
															aria-label="Name: activate to sort column descending"
															style="width: 120px;">사원번호</th>
														<th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Office: activate to sort column ascending"
															style="width: 100px;">부서</th>
														<th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Age: activate to sort column ascending"
															style="width: 90px;">직급</th>
														<th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Age: activate to sort column ascending"
															style="width: 90px;">근태 현황 (한 달)</th>
														<th class="sorting" tabindex="0" aria-controls="dataTable"
														rowspan="1" colspan="1"
														aria-label="Age: activate to sort column ascending"
														style="width: 80px;"></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${employeeList}" var="e">
														<tr>
															<td>
																<c:choose>
																	<c:when test="${not empty e.saveName}">
																		<a href="./detail?userId=${e.userId}">
																			<img src="/coffice/upload/profile/${e.saveName}" width="80" height="80" >
																		</a>
																	</c:when>
																	<c:otherwise>
																		<a href="./detail?userId=${e.userId}">
																			<img src="/images/coffice.png" alt="Default Profile" width="80" height="80">
																		</a>
																	</c:otherwise>
																</c:choose>															</td>
															<td><a href="./detail?userId=${e.userId}">${e.name}</a></td>
															<td>${e.userId}</td>
															<td>${e.deptName}</td>
															<td>${e.position}</td>
															<td>
																<a href="/attendance/statusUpdate?userId=${e.userId}">
																	<fmt:formatNumber value="${percentMap[e.userId]}" pattern="#0.0"/>%
																</a>
															</td>
															<td>
												                <a href="./update?userId=${e.userId}" class="btn btn-sm btn-primary">수정</a>
												            </td>
															
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