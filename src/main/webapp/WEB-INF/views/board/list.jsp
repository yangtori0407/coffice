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
<script type="module"
	src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule
	src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<style>
.boardA {
	color: rgb(87, 178, 230);
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
					<!-- Page Heading -->
					<!-- <h1 class="mb-2 text-gray-800">익명게시판</h1> -->

					<!-- DataTales Example -->
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
														<option value="k3">제목</option>
														<option value="k2">본문</option>
														<option value="k1">제목+본문</option>
													</select> <input type="search" name="search"
														class="form-control form-control-sm mr-2"
														placeholder="검색어를 입력하세요" style="max-width: 200px;">


													<button type="submit" class="btn btn-secondary">검색</button>

												</form>

												<!-- 추가 버튼 -->
												<a href="./add" class="btn btn-primary">작성하기</a>
											</div>
										</div>
									</div>
									<div class="row" style="height: 580px;">
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
															style="width: 100.788px;">글번호</th>
														<th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Position: activate to sort column ascending"
															style="width: 450px;">글제목</th>
														<th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Office: activate to sort column ascending"
															style="width: 116.788px;">댓글수</th>
														<!-- <th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Age: activate to sort column ascending"
															style="width: 116.788px;">조회수</th> -->
														<th class="sorting" tabindex="0" aria-controls="dataTable"
															rowspan="1" colspan="1"
															aria-label="Age: activate to sort column ascending"
															style="width: 116.788px;">작성일</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${list }" var="l">
														

															<tr>
																<td><a class="boardA"
																	href="./detail?boardNum=${l.boardNum }">${l.boardNum }</a></td>
																<td style="font-weight: bold;"><a class="boardA"
																	href="./detail?boardNum=${l.boardNum }">${l.boardTitle }</a></td>
																<td>${l.commentCount }</td>
																<%-- <td>${l.boardHit }</td> --%>
																<td>${l.boardDate }</td>
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
												<ul class="pagination">
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


													<li
														class="paginate_button page-item next ${pager.endCheck == false ? '' : 'disabled' }
														id="dataTable_next"><a
														href="./list?nowPage=${pager.end+1 }&search=${pager.search}&kind=${pager.kind}"
														aria-controls="dataTable" data-dt-idx="7" tabindex="0"
														class="page-link">Next</a></li>
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