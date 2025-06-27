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
					<div class="card shadow mb-4">
					<div class="card-body">
					<div>
					<div id="dataTable_wrapper"
						class="dataTables_wrapper dt-bootstrap4">
					<div class="row">
					<div class="col-12 mb-4">
							<div class="d-flex justify-content-between align-items-center">
								<form method="get" class="form-inline d-flex align-items-center">
									<input type="hidden" name="ingredientsID" value="${vo.ingredientsID}"/>
										 <select name="kind" class="custom-select mr-2">
											<option value="k1">상태</option>
											<option value="k2">등록자</option>
											<option value="k3">날짜</option>
										  </select>			 
											<input type="text" name="search" id="keyword" class="form-control form-control-sm mr-2" placeholder="검색어를 입력하세요" style="max-width: 200px;">
											<div class="input-group-append">
										<button class="btn btn-secondary" type="submit" id="button-addon2">찾기</button>
									</div>
								</form>
								
								<div class="ml-3" style="padding: 10px 20px; background-color: #f8f9fa;
             						border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); font-weight: 500;">
									재고: ${vo.ingredientsStock} /
									가격: ₩${vo.ingredientsPrice}
								</div>
					</div>
					</div>
					</div>
					<div class="row">
					<div class="col-sm-12">
					<table class="table table-bordered dataTable" id="dataTable"
							width="100%" cellspacing="0" role="grid"
							aria-describedby="dataTable_info" style="width: 100%;">
						<thead>
							<tr>		
								<th class="sorting sorting_asc" tabindex="0"
									aria-controls="dataTable" rowspan="1" colspan="1"
									aria-sort="ascending"
									aria-label="Name: activate to sort column descending"
									style="width: 100.788px;">상태</th>
								<th class="sorting" tabindex="0" aria-controls="dataTable"
									rowspan="1" colspan="1"
									aria-label="Position: activate to sort column ascending"
									style="width: 450px;">수량</th>
								<th class="sorting" tabindex="0" aria-controls="dataTable"
									rowspan="1" colspan="1"
									aria-label="Office: activate to sort column ascending"
									style="width: 116.788px;">등록</th>
								<th class="sorting" tabindex="0" aria-controls="dataTable"
									rowspan="1" colspan="1"
									aria-label="Age: activate to sort column ascending"
									style="width: 116.788px;">날짜</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="h">
							<c:forEach items="${h.history}" var="item">
							<tr>
								<td style="color:${item.receive?'blue':'red'};">${item.receive?"입고":"출고"}</td>
								<td>${item.number}</td>
								<td>${item.userVO.name}</td>
								<td>${item.registrationDate}</td>			
							</tr>
							</c:forEach>
							</c:forEach>
						</tbody>
					</table>
					</div>
					</div>
				<div class="row">
					<div class="col-sm-12 col-md-5"></div>
							<div class="col-sm-12 col-md-7">
								<div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate">
									<ul class="pagination">
										<li class="paginate_button page-item previous disabled" id="dataTable_previous">
											<a href="./detail?ingredientsID=${vo.ingredientsID}&nowPage=${pager.start-1 }&search=${pager.search}&kind=${pager.kind}" aria-controls="dataTable" data-dt-idx="0" tabindex="0" class="page-link">
												Previous
											</a>
										</li>
										<c:forEach begin="${pager.start}" end="${pager.end}" var="i">
										<li class="paginate_button page-item ${pager.nowPage == i ? 'active' : '' }">
											<a href="./detail?ingredientsID=${vo.ingredientsID}&nowPage=${i}&search=${pager.search}&kind=${pager.kind}" aria-controls="dataTable" data-dt-idx="1" tabindex="0" class="page-link">
											${i}
											</a>
										</li>
										</c:forEach>
										<li class="paginate_button page-item next ${pager.endCheck?'disabled':''}" id="dataTable_next">
											<a href="./detail?ingredientsID=${vo.ingredientsID}&nowPage=${pager.end+1}&search=${pager.search}&kind=${pager.kind}" aria-controls="dataTable" data-dt-idx="7" tabindex="0" class="page-link">
											Next
											</a>
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
		</div>
		<!-- End Content Wrapper -->
	
	<!-- End Wrapper -->
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
</html>