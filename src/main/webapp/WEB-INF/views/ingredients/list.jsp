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
					<div class="row" style="margin: 0 auto; width: 600px">
						<div class="col-12 mb-4">
							<div class="d-flex justify-content-between align-items-center">
								<form method="get" class="form-inline d-flex align-items-center">
										 <select name="kind" class="custom-select mr-2">
											<option value="k1">이름</option>
											<option value="k2">재고</option>
											<option value="k3">등록날짜</option>
										  </select>			 
											<input type="text" name="search" id="keyword" class="form-control form-control-sm mr-2" placeholder="검색어를 입력하세요" style="max-width: 200px;">
											<div class="input-group-append">
										<button class="btn btn-secondary" type="submit" id="button-addon2">찾기</button>
									</div>
								</form>
								
								<a href="./add" class="btn btn-primary">추가하기</a>
							</div>
						</div>
					</div>
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
									<td><a href="./detail?ingredientsID=${vo.ingredientsID}">${vo.ingredientsName}</a></td>
									<td>${vo.ingredientsStock}</td>
									<td>${vo.ingredientsDate}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
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
								<div class="col-sm-12 col-md-4">
									<a href="#" class="btn btn-danger" data-toggle="modal" data-target="#addReceive">입/출고</a>
								</div>
						</div>
					</div>
				</div>
			<!-- end Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
	</div>
		<div class="modal" id="addReceive" tabindex="-1">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">입/출고</h5>
		            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
		                <span aria-hidden="true">×</span>
		            </button>
		      </div>
		      <div class="modal-body" aria-label="Default select example">
			        <div class="form-check">
					  <input class="form-check-input" type="radio" name="receive" id="input" value="true">
					  <label class="form-check-label" for="radioDefault1">
					    입고
					  </label>
					</div>
					<div class="form-check">
					  <input class="form-check-input" type="radio" name="receive" id="output" value="false">
					  <label class="form-check-label" for="radioDefault2">
					    출고
					  </label>
					</div>
					
			        <select class="form-select" id="selectIngredients">
				       <option selected>상품을 선택해주세요</option>
				       <c:forEach items="${list}" var="vo">
				       		<option value="${vo.ingredientsID}">${vo.ingredientsName}</option>
				       </c:forEach>
			        </select>
			        <input type="text" name="number" id="number">
			        <input type='button' onclick='count("plus")' value='+'/>
					<input type='button' onclick='count("minus")' value='-'/>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal" type="button">Close</button>
			        <button class="btn btn-primary" id="addReceiveBtn">등록</button>
			      </div>
			  </div>
		    </div>
		  </div>
		</div>
	<!-- End Wrapper -->
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
<script src="/js/ingredients/history.js"></script>
</html>