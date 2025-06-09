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
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">

					<!-- contents 내용 -->
					<div class="card-body">
					<form method="get">
						<div class="input-group" style="margin:20px auto;width: 600px;">
								 <select name="kind" class="form-control col-3" id="exampleFormControlSelect1">
									<option value="k1">이름</option>
									<option value="k2">재고</option>
									<option value="k3">등록날짜</option>
								  </select>			 
									<input type="text" name="search" id="keyword" class="form-control" aria-label="Recipient's username" aria-describedby="button-addon2">
									<div class="input-group-append">
								<button class="btn btn-secondary" type="submit" id="button-addon2">찾기</button>
							</div>
						</div>
					</form>
					<table class="table table-striped" style="margin:20px auto;width: 600px;">
						<thead>
							<tr>
								<th>No.</th>
								<th>이름</th>
								<th>재고</th>
								<th>등록날짜</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="vo">
								<tr>
									<td>${vo.ingredientsID}</td>
									<td>${vo.ingredientsName}</td>
									<td>${vo.ingredientsStock}</td>
									<td>${vo.ingredientsDate}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
					<div class="row">
						<div class="col-sm-12 col-md-5"></div>
							<div class="col-sm-12 col-md-7">
								<div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate">
									<ul class="pagination">
										<li class="paginate_button page-item previous disabled" id="dataTable_previous">
											<a href="./list?nowPage=${pager.start-1 }&search=${pager.search}&kind=${pager.kind}" aria-controls="dataTable" data-dt-idx="0" tabindex="0" class="page-link">
												Previous
											</a>
										</li>
										<c:forEach begin="${pager.start }" end="${pager.end }" var="i">
										<li class="paginate_button page-item ${pager.nowPage == i ? 'active' : '' }">
											<a href="./list?nowPage=${i }&search=${pager.search}&kind=${pager.kind}" aria-controls="dataTable" data-dt-idx="1" tabindex="0" class="page-link">
											${i}
											</a>
										</li>
										</c:forEach>
										<li class="paginate_button page-item next ${pager.endCheck?'disabled':''}" id="dataTable_next">
											<a href="./list?nowPage=${pager.end+1}&search=${pager.search}&kind=${pager.kind}" aria-controls="dataTable" data-dt-idx="7" tabindex="0" class="page-link">
											Next
											</a>
										</li>
									</ul>
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