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
<link href="/css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
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
										 <select name="kind" class="custom-select mr-2">
											<option value="k1">이름</option>
											<option value="k2">재고</option>
											<option value="k3">등록날짜</option>
										  </select>			 
											<input type="search" name="search" id="keyword" class="form-control form-control-sm mr-2" placeholder="검색어를 입력하세요" style="max-width: 200px;">
											<div class="input-group-append">
										<button class="btn btn-secondary" type="submit" id="button-addon2">찾기</button>
									</div>
								</form>
								
								
								<a href="#" class="btn btn-primary" data-toggle="modal" data-target="#addIngredients">추가하기</a>
								
							</div>
						</div>
					</div>
					<table class="table table-bordered dataTable" id="dataTable"
												width="100%" cellspacing="0" role="grid"
												aria-describedby="dataTable_info" style="width: 100%;">
						<thead>
							<tr>
								<th class="" tabindex="0"
									aria-controls="dataTable" rowspan="1" colspan="1"
									aria-sort="ascending"
									aria-label="Name: activate to sort column descending"
									style="width: 100.788px;">No.</th>
								<th class="sorting" tabindex="0" aria-controls="dataTable"
									rowspan="1" colspan="1"
									aria-label="Position: activate to sort column ascending"
									style="width: 150px;">
									이름
									</th>
								<th class="sorting" tabindex="0" aria-controls="dataTable"
									rowspan="1" colspan="1"
									aria-label="Age: activate to sort column ascending"
									style="width: 116.788px;">재고</th>
								<th class="sorting" tabindex="0" aria-controls="dataTable"
									rowspan="1" colspan="1"
									aria-label="Age: activate to sort column ascending"
									style="width: 116.788px;">등록날짜</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="vo">
								<tr>
									<td>${vo.ingredientsID}</td>
									<td><a href="./detail?ingredientsID=${vo.ingredientsID}">${vo.ingredientsName}</a></td>
									<td>${vo.ingredientsStock}</td>
									<td>${vo.ingredientsDate}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
						<div class="row">
							<div class="col-sm-12 col-md-4"></div>
								<div class="col-sm-12 col-md-4">
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
									<div class="col-sm-12 col-md-4" style="text-align:right;">
										<a href="#" class="btn btn-danger" data-toggle="modal" data-target="#addReceive">입/출고</a>
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
		<div class="modal fade" id="addReceive" tabindex="-1" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content shadow content">
		      <div class="modal-header bg-dark text-white">
		        <h5 class="modal-title"><i class="fas fa-box-open mr-2"></i>입/출고</h5>
		            <button class="close text-white" type="button" data-dismiss="modal" aria-label="Close">
		                <span aria-hidden="true">&times;</span>
		            </button>
		      </div>
		      <div class="modal-body p-4">
		      	<div class="form-group mb-3">
			        <div class="form-check form-check-inline">
					  <input class="form-check-input" type="radio" name="receive" id="input" value="true">
					  <label class="form-check-label text-success font-weight-bold" for="input">
					     <i class="fas fa-arrow-down"></i>입고
					  </label>
					</div>
					<div class="form-check form-check-inline">
					  <input class="form-check-input" type="radio" name="receive" id="output" value="false">
					  <label class="form-check-label text-danger font-weight-bold" for="output">
					     <i class="fas fa-arrow-up"></i>출고
					  </label>
					</div>
				</div>
				<div class="form-group mb-3">
					<label for="selectIngredients" class="font-weight-bold">상품</label>
			        <select class="form-select form-control" id="selectIngredients" required>
				       <option value="" selected>상품을 선택해주세요</option>
				       <c:forEach items="${totalList}" var="vo">
				       		<option value="${vo.ingredientsID}">${vo.ingredientsName}</option>
				       </c:forEach>
			        </select>
				</div>
				<div class="form-group mb-3">
					<label for="number" class="font-weight-bold">수량</label>
			        <input type="number" class="form-control" name="number" id="number" min="0">
				</div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal" type="button"><i class="fas fa-times"></i>Close</button>
			        <button class="btn btn-primary" id="addReceiveBtn"><i class="fas fa-plus-circle"></i>등록</button>
			      </div>
			  </div>
		    </div>
		  </div>
		</div>
		
		<div class="modal fade" id="addIngredients" tabindex="-1" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content shadow rounded">
		      <div class="modal-header bg-primary text-white">
		        <h5 class="modal-title"><i class="fas fa-plus-circle mr-2"></i>상품추가</h5>
		            <button class="close text-white" type="button" data-dismiss="modal" aria-label="Close">
		                <span aria-hidden="true">&times;</span>
		            </button>
		      </div>
		      <div class="modal-body p-4">
					<form:form modelAttribute="ingredientsVO" action="./add" method="post">
					<div class="form-group mb-3">
						<label for="ingredientsName" class="font-weight-bold">상품 이름</label>
						<form:input path="ingredientsName" class="form-control" id="ingredientsName" placeholder="ex)고구마" required="true"/>
					</div>

						<div class="form-group mb-3">
							<label for="ingredientsPrice" class="font-weight-bold">상품 가격</label>
							<form:input path="ingredientsPrice" class="form-control" id="ingredientsPrice" placeholder="ex)1000" required="true"/>					
						</div>
						<div class="text-right">
							<button type="button" class="btn btn-success" id="addIngredientsBtn"><i class="fas fa-check"></i> 상품등록</button>
						</div>				
					</form:form>
			  </div>
		    </div>
		  </div>
		</div>
	<!-- End Wrapper -->
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
	<script src="/js/ingredients/list.js"></script>
	<script src="/js/ingredients/history.js"></script>
    <!-- Custom scripts for all pages-->
    <script src="/js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="/vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="/vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script>
	
	  $('#dataTable').DataTable({
		paging:false,
		searching:false,
		info:false,
	    ordering: true,
	    /* columnDefs: [
	    	{targets:0, orderable:false}
	    ] */
	  
	  });
	
</script>
</html>