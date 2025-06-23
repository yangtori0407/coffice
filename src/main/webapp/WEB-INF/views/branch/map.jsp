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
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${appkey}&libraries=services"></script>
<link href="/css/branch/map.css" rel="stylesheet">
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
					<div id="map">
					<div class="custom_zoomcontrol radius_border">
						<span id="reloadMap">
							<ion-icon name="refresh-outline"></ion-icon>
						</span>
					</div>
					</div>
					<div class="card-body">
					<div class="input-group" style="margin:20px auto;width: 600px; display: flex; align-items: center;">
						<div style="display: inline-block; padding: 10px 20px; background-color: #f8f9fa; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
							총매출:₩${total}
						</div>
					</div>
					<form method="get">
					<div class="search-box">
							 <select name="kind" id="exampleFormControlSelect1">
								<option value="k1">지점이름</option>
								<option value="k2">운영상태</option>
								<option value="k3">주소</option>
							  </select>
											 
								<input type="text" name="search" id="keyword" placeholder="검색어 입력">
								
							<button type="submit" id="button-addon2">찾기</button>
						
					</div>
					</form>
					<table class="table-custom">
						<thead>
							<tr>
								<th>지점번호</th>
								<th>지점이름</th>
								<th>운영상태</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="vo">
								<tr>
									<td>${vo.branchId}</td>
									<td class="detail-link" data-branch="${vo.branchId}" style="cursor:pointer">${vo.branchName}</td>
									<td style="color:${vo.branchStatus?'green':'red'};">
										${vo.branchStatus?"운영중":"운영안함"}
									</td>
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
							<div style="width: 600px; margin: 0 auto; text-align: right;">
								<a class="btn btn-success" href="./api/excel/download/branch">지점정보다운</a>
								<a class="btn btn-primary" href="#" data-toggle="modal" data-target="#addBranch">
									점주지정등록
								</a>							
							</div>
						</div>
					</div>
					<div>
					</div>
				</div>
			</div>
			<!-- end Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
	</div>
	<!-- 점주지점등록 modal-->
	<div class="modal" id="addBranch" tabindex="-1" aria-hidden="true">
		  <div class="modal-dialog modal-lg">
		    <div class="modal-content shadow rounded">
		      <div class="modal-header bg-primary text-white">
		        <h5 class="modal-title"><i class="fas fa-user-plus mr-2"></i>지점점주등록</h5>
		            <button class="close text-white" type="button" data-dismiss="modal" aria-label="Close">
		                <span aria-hidden="true">&times;</span>
		            </button>
		      </div>
		      <div class="modal-body p-4">
		      	 <div class="form-group mb-3">
		      	 <label for="selectUser" class="font-weight-bold">점주 선택</label>
			        <select class="form-select form-control" id="selectUser" required>
			            <option value="" selected>추가할 점주를 선택하세요</option>
						<c:forEach items="${notAddBranchMasterList}" var="m">
							<option value="${m.userId.userId}">${m.userId.name}</option>
						</c:forEach>
			        </select>
			        </div>
					<div class="form-group mb-3">
					 <label for="selectBranch" class="font-weight-bold">지점 선택</label>
			        <select class="form-select form-control" id="selectBranch">
				            <option value="" selected>추가할 지점을 선택하세요</option>
						<c:forEach items="${notAddBranchList}" var="b" varStatus="s">
				            <option value="${s.current.branchId}">${b.branchName},${s.current.branchId}</option>					
						</c:forEach>
			        </select>
			        </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal" type="button"><i class="fas fa-times"></i>닫기</button>
			        <button class="btn btn-primary" id="addBranchBtn"><i class="fas fa-plus-circle"></i>등록</button>
			      </div>
			  </div>
		    </div>
		  </div>
		</div>
		
		
		<!-- 지점 detail modal -->
		<div class="modal" id="detailBranch" tabindex="-1">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">지점정보</h5>
		            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
		                <span aria-hidden="true">×</span>
		            </button>
		      </div>
		      <div class="modal-body" aria-label="Default select example" style="margin:0 auto;">
			     <div>지점이름:<span id="detailBranchName"></span></div>
			     <div>점주:<span id="detailUserName"></span></div>
			     <div>운영상태:<span id="detailStatus"></span></div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal" type="button">Close</button>
			      </div>
			  </div>
		    </div>
		  </div>
		</div>
	<!-- End Wrapper -->
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
<!-- branchList -->
<script>
	let addressList = [
		<c:forEach items="${list}" var="vo" varStatus="s">
			{
				id: "${vo.branchId}",
				name:"${vo.branchName}",
				address:"${vo.branchAddress}",
				status:"${vo.branchStatus?"운영중":"운영안함"}"
			}<c:if test="${!s.last}">,</c:if>		
		</c:forEach>
		];
</script>
<script src="/js/branch/map.js"></script>
</html>