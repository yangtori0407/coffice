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
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">
					<div id="map" style="width:500px;height:400px;min-height: 50vh; margin: 0 auto">
					<div class="custom_zoomcontrol radius_border">
						<span id="reloadMap">
							<ion-icon name="refresh-outline"></ion-icon>
						</span>
					</div>
					</div>
					<div class="card-body">
					<form method="get">
					<div class="input-group" style="margin:20px auto;width: 600px;">
							 <select name="kind" class="form-control col-3" id="exampleFormControlSelect1">
								<option value="k1">지점이름</option>
								<option value="k2">운영상태</option>
								<option value="k3">주소</option>
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
					</div>
					<div>
					</div>
					<div style="width: 600px; margin: 0 auto; text-align: right;">
						<a class="btn btn-success" href="./api/excel/download">지점정보다운</a>
					     <a class="btn btn-primary" href="#" data-toggle="modal" data-target="#addBranch">
					       점주지정등록
					      </a>							
					</div>
					
				</div>
			</div>
			<!-- end Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
	</div>
	<!-- 점주지점등록 modal-->
	<div class="modal" id="addBranch" tabindex="-1">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title">지점점주등록</h5>
		            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
		                <span aria-hidden="true">×</span>
		            </button>
		      </div>
		      <div class="modal-body" aria-label="Default select example">
			        <select class="form-select" id="selectUser">
			            <option selected>추가할 점주를 선택하세요</option>
						<c:forEach items="${notAddBranchMasterList}" var="m">
							<option value="${m.userId.userId}">${m.userId.name}</option>
						</c:forEach>
			        </select>
					
			        <select class="form-select" id="selectBranch">
				            <option selected>추가할 지점을 선택하세요</option>
						<c:forEach items="${notAddBranchList}" var="b" varStatus="s">
				            <option value="${s.current.branchId}">${b.branchName},${s.current.branchId}</option>					
						</c:forEach>
			        </select>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal" type="button">Close</button>
			        <button class="btn btn-primary" id="addBranchBtn">add</button>
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