<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${appkey}&libraries=services"></script>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">

					<div id="map" style="width:500px;height:400px;min-height: 50vh; margin: 0 auto"></div>

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
									<td>${vo.branchName}</td>
									<td style="color:${vo.branchStatus?'green':'red'};">
										${vo.branchStatus?"운영중":"운영안함"}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div style="width: 600px; margin: 0 auto; text-align: right;">
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
		        <h5 class="modal-title">Modal title</h5>
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
	<!-- End Wrapper -->
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
<!-- branchList -->
<script>
	let addressList = [
		<c:forEach items="${list}" var="vo" varStatus="s">
			{
				name:"${vo.branchName}",
				address:"${vo.branchAddress}",
				status:"${vo.branchStatus?1:0}"
			}<c:if test="${!s.last}">,</c:if>		
		</c:forEach>
		];
</script>
<script src="/js/branch/map.js"></script>
</html>