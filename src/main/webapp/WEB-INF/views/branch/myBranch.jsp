<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
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
					<div style="width:500px; height:250px; border:3px solid #aaa; margin:20px auto;">
						<canvas id="chart"></canvas>
					</div>
				<div style="width:500px; margin:20px auto; text-align:center">
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
					<table class="table table-striped" style="margin:20px auto;width: 600px;">
						<thead>
							<tr>
								<th>상태</th>
								<th>금액</th>
								<th>수량</th>
								<th>메뉴</th>
								<th>날짜</th>
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
						<div style="width: 600px; margin: 0 auto; text-align: right;">
							<a class="btn btn-primary" href="#" data-toggle="modal" data-target="#order">
								주문
							</a>	
							<a class="btn btn-success" href="./api/excel/download/sale">매출다운</a>						
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
	
	<div class="collapse side-menu-right" id="sideMenu">
	  <div class="bg-light border p-4" 
	       style="position: fixed; top: 0; left: 0; height: 100vh; width: 250px; z-index: 1050; box-shadow: 2px 0 5px rgba(0,0,0,0.1); overflow-y: auto;">
	    
	    <h5 class="mb-4" style="text-align:center; font-weight: bold;">☕ 카페 메뉴판</h5>

	    <div style="display: flex; flex-direction: column; gap: 20px;">
	      <c:forEach items="${menuList}" var="m">
	        <div style="background: #fff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); padding: 10px; display: flex; flex-direction: column; align-items: center;">
	          <!-- 이미지 자리 -->
	          <img src="/img/커피지도마커3.png" alt="${m.menuName}" 
	               style="width: 100%; height: 120px; object-fit: cover; border-radius: 6px; margin-bottom: 8px;">
	          
	          <!-- 메뉴명 + 가격 -->
	          <div style="display: flex; justify-content: space-between; width: 100%;">
	            <span style="font-size: 14px; font-weight: 500;">${m.menuName}</span>
	            <span style="font-size: 14px; font-weight: bold;">₩${m.menuPrice}</span>
	          </div>
	        </div>
	      </c:forEach>
	    </div>

	    <button class="btn btn-danger mt-4" data-toggle="collapse" data-target="#sideMenu" style="width:100%;">닫기</button>
	  </div>
	</div>
	
	<div class="modal" id="order" tabindex="-1">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">상품주문</h5>
		            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
		                <span aria-hidden="true">×</span>
		            </button>
		      </div>
		      <div class="modal-body" aria-label="Default select example" id="orderBody">
		      		<div class="form-check">
					  <input class="form-check-input" type="radio" name="salesType" id="import" value="true">
					  <label class="form-check-label" for="radioDefault1">
					    수입
					  </label>
					</div>
					<div class="form-check">
					  <input class="form-check-input" type="radio" name="salesType" id="expenditure" value="false">
					  <label class="form-check-label" for="radioDefault2">
					    지출
					  </label>
					</div>
			        <select class="form-select" id="selectMenu">
			            <option selected>수입/지출을 선택해주세요</option>
			        </select>
					
			        <input type="text" name="salesQuantity" id="salesQuantity" placeholder="수량을 입력하세요">
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal" type="button">Close</button>
			      </div>
			  </div>
		    </div>
		  </div>
		</div>
		
			<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
			<script src="/js/branch/mybranch.js">></script>
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