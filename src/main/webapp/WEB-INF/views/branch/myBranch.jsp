<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>COFFICE</title>
<link href="/images/3.png" rel="shortcut icon" type="image/x-icon">
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<script src= "https://cdn.jsdelivr.net/npm/chart.js"></script>
<link rel="stylesheet" type="text/css" href="/css/branch/mybranch.css">
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">
				<div class="card shadow mb-4">
					<div style="width:500px; height:250px; border:3px solid #aaa; margin:20px auto;">
						<canvas id="chart"></canvas>
					</div>
				<div style="width:600px; margin:20px auto; text-align:center">
					<div style="display: inline-block; padding: 10px 20px; background-color: #f8f9fa; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
						<c:forEach items="${list}" var="l" varStatus="s">
							<c:if test="${s.first}">
								${l.branchName}(${l.branchAddress})매출:
							</c:if>
						</c:forEach>	
							₩${total}	
					</div>
					<button class="btn btn-light shadow-sm rounded-circle" 
							type="button" data-toggle="collapse" data-target="#sideMenu" 
							aria-expanded="false">
					  <ion-icon name="menu-outline" style="font-size: 24px; color: #333;"></ion-icon>
					</button>
				</div>
					<div class="row">
					<div class="col-sm-12">
					<table class="table table-bordered dataTable" id="dataTable"
							width="100%" cellspacing="0" role="grid"
							aria-describedby="dataTable_info" style="width: 98%;margin:0 auto">
						<thead>
							<tr role="row">
										<th class="sorting sorting_asc" tabindex="0"
											aria-controls="dataTable" rowspan="1" colspan="1"
											aria-sort="ascending"
											aria-label="Name: activate to sort column descending"
											style="width: 100.788px;">상태</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1"
											aria-label="Position: activate to sort column ascending"
											style="width: 450px;">금액</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1"
											aria-label="Office: activate to sort column ascending"
											style="width: 116.788px;">수량</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1"
											aria-label="Age: activate to sort column ascending"
											style="width: 116.788px;">메뉴</th>
										<th class="sorting" tabindex="0" aria-controls="dataTable"
											rowspan="1" colspan="1"
											aria-label="Age: activate to sort column ascending"
											style="width: 116.788px;">날짜</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="h">
								<c:forEach items="${h.salesVO}" var="item">
									<tr>
										<td style="color:${item.salesType?'blue':'red'};">${item.salesType?"수입":"지출"}</td>
										<td>₩${item.salesProfit}</td>
										<td>${item.salesQuantity}</td>			
										<td>${item.salesType ? item.menuVO.menuName:item.ingredientsVO.ingredientsName}</td>
										<td>${item.salesDate}</td>
									</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="row" style="margin:20px 0">
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
						<div style="width: 96%; margin: 20px auto; text-align: right;">
							<sec:authorize access="hasAnyAuthority('6')">				
							<a class="btn btn-success" href="./api/excel/download/sale">매출다운</a>
							<a class="btn btn-primary" href="#" data-toggle="modal" data-target="#order">
								주문
							</a>
							</sec:authorize>
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
	
	<!-- 메뉴리스트 sidebar -->
	<div class="collapse side-menu-right" id="sideMenu">
	  <div class="bg-light border p-4" 
	       style="position: fixed; top: 0; left: 0; height: 100vh; width: 250px; z-index: 1050; box-shadow: 2px 0 5px rgba(0,0,0,0.1); overflow-y: auto;">
	    
	    <h5 class="mb-4" style="text-align:center; font-weight: bold;">☕ 카페 메뉴판</h5>

	    <div style="display: flex; flex-direction: column; gap: 20px;">
	      <c:forEach items="${menuList}" var="m">
	        <div style="background: #fff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); padding: 10px; display: flex; flex-direction: column; align-items: center;">
	          <!-- 이미지 자리 -->
	          <c:choose>
				<c:when test="${not empty m.saveName}">
					<img src="/app/upload/menu/${m.saveName}"  alt="${m.menuName}" 
	               		style="width: 100%; height: 120px; object-fit: cover; border-radius: 6px; margin-bottom: 8px;" >
				</c:when>
				<c:otherwise>
					<img src="/images/noImage.jpg" alt="${m.menuName}" 
	              	 style="width: 100%; height: 120px; object-fit: cover; border-radius: 6px; margin-bottom: 8px;">
				</c:otherwise>
				</c:choose>
	          <!-- 메뉴명 + 가격 -->
	          <div style="display: flex; justify-content: space-between; width: 100%;">
	            <span style="font-size: 14px; font-weight: 500;">${m.menuName}</span>
	            <span style="font-size: 14px; font-weight: bold;">₩${m.menuPrice}</span>
	          </div>
	        </div>
	      </c:forEach>
	    </div>
		<sec:authorize access="hasAuthority('3')">
		<a class="btn btn-primary mt-4" href="#" data-toggle="modal" data-target="#addMenu" style="width:100%;">메뉴추가</a>
		</sec:authorize>
	    <button class="btn btn-danger mt-4" data-toggle="collapse" data-target="#sideMenu" style="width:100%;">닫기</button>
	  </div>
	</div>
	<!-- 수입,지출 -->
	<div class="modal fade" id="order" tabindex="-1" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content shadow rounded">
		      <div class="modal-header bg-dark text-white">
		        <h5 class="modal-title"><i class="fas fa-shopping-cart mr-2"></i>상품주문</h5>
		            <button class="close text-white" type="button" data-dismiss="modal" aria-label="Close">
		                <span aria-hidden="true">&times;</span>
		            </button>
		      </div>
		      <div class="modal-body" aria-label="Default select example" id="orderBody">
		      		<div class="form-check form-check-inline">
					  <input class="form-check-input" type="radio" name="salesType" id="import" value="true">
					  <label class="form-check-label text-success font-weight-bold" for="import">
					    <i class="fas fa-arrow-down"></i>수입
					  </label>
					</div>
					<div class="form-check form-check-inline">
					  <input class="form-check-input" type="radio" name="salesType" id="expenditure" value="false">
					  <label class="form-check-label text-danger font-weight-bold" for="expenditure">
					    <i class="fas fa-arrow-up"></i>지출
					  </label>
					</div>
					<div class="form-group mt-3">
				        <select class="form-control" id="selectMenu" required>
				            <option value="" selected>수입/지출을 선택해주세요</option>
				        </select>					
					</div>
					<div class="form-group">
				        <input type="number" class="form-control" name="salesQuantity" id="salesQuantity" placeholder="수량을 입력하세요" min="0">
					</div>
			      <div class="modal-footer" id="orderFooter">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal" type="button">Close</button>
			      </div>
			  </div>
		    </div>
		  </div>
		</div>
		<!-- 메뉴추가 -->
		<div class="modal fade" id="addMenu" tabindex="-1" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content shadow rounded">
		      <div class="modal-header bg-primary text-white">
		        <h5 class="modal-title"><i class="fas fa-plus-circle mr-2"></i>메뉴추가</h5>
		            <button class="close text-white" type="button" data-dismiss="modal" aria-label="Close">
		                <span aria-hidden="true">&times;</span>
		            </button>
		      </div>
		      <div class="modal-body p-4">
					<form:form modelAttribute="menuVO" action="./addMenu" method="post" enctype="multipart/form-data">
					<div class="form-group mb-3">
						<label for="menuName" class="font-weight-bold">메뉴 이름</label>
						<form:input path="menuName" class="form-control" id="menuName" placeholder="ex)아메리카노" required="true"/>
					</div>

						<div class="form-group mb-3">
							<label for="menuPrice" class="font-weight-bold">메뉴 가격</label>
							<input type="number" name="menuPrice" class="form-control" id="menuPrice" placeholder="ex)1000" required/>					
						</div>
						
						<div class="form-group mb-3">
							<label for="menuFile" class="font-weight-bold">메뉴 이미지</label>
							<input type="file" value="" id="menuFile" class="form-control" name="menuFile" >
						</div>
						<div class="text-right">
							<button type="button" class="btn btn-success" id="addMenuBtn"><i class="fas fa-check"></i> 메뉴등록</button>
						</div>				
					</form:form>
			  </div>
		    </div>
		  </div>
		</div>
		
			<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
			<script src="/js/branch/mybranch.js"></script>
</body>
<script>
	
	const labels = 
		[
		<c:forEach items="${chart}" var="c" varStatus="s">
				"${c.salesDate}"
			<c:if test="${!s.last}">,</c:if>
		</c:forEach>
		];
	

	const value = 
		[
		<c:forEach items="${chart}" var="c" varStatus="s">
			"${c.totalSale}"
			<c:if test="${!s.last}">,</c:if>
		</c:forEach>
		];

		
	const chart = document.getElementById("chart");
	new Chart(chart,{
		type:'line',
		data:{
			  labels: labels,
			  datasets:[{
				label: '매출',
				data: value,
				fill: false,
				backgroundColor: '#36A2EB',
				borderWidth: 1,
				borderColor: '#36A2EB'
			  }]
		},
		options:{
			scales:{
				yAxes: [{
			          stacked: true,
			          gridLines: {
			               display: true,
			               color: "rgba(0,0,0,0.1)",
			          },
			          ticks:{
			               beginAtZero: true
			          }
				}]
			}
		}
	})
</script>
</html>