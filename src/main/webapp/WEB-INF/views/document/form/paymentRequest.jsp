<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

			  <div class="whole-content row m-0 p-0" style="width: 1200px;">
			  	
			  	<div class="left-content" style="width: 150px; border: 1px solid #000;">
				  
				</div>
			  
				  <div class="middle-content" style="width: 800px; box-sizing: border-box;">  
				
				    <!-- 상단 영역 -->
				    <div class="row m-0 p-0" style="border: 1px solid #000;">	
				
				      <!-- 좌측: 번호 + 양식 이름 -->
				      <div class="top-left col-4 p-0 m-0" style="border-right: 1px solid #000;">
				        <div class="d-flex align-items-center justify-content-center" style="border-bottom: 1px solid #000; padding: 8px; height: 20%;">문서 번호</div>
				        <div class="d-flex align-items-center justify-content-center" style="padding: 8px; height: 80%;">양식 이름</div>
				      </div>
	
				      <!-- 결재자 정보 -->
				      <div class="top-right col-8 row g-0 p-0 m-0" style="border-left: 1px solid #000;">
						  <c:forEach items="${vo.approvalLineVOs}" var="i" varStatus="status">
						    <div class="col-3 p-1">
						      <div class="approver-wrapper text-center h-100" style="border: 1px solid #000;">
						        <div class="grade-title" style="border-bottom: 1px solid #000; font-weight: bold;">${i.userPosition}</div>
						        <div class="stamp-box" style="border-top: 1px solid #000; border-bottom: 1px solid #000; width: 100%; height: 120px; margin: 0 auto;">
						          <c:if test="${not empty i.signVO}">
						            <img src="/signs/${i.signVO.saveName}" style="width: 100%; height: 100%; object-fit: contain;" />
						          </c:if>
						        </div>
						        <div class="approver_name" style="border-bottom: 1px solid #000; font-weight: bold;">${i.userName}</div>
						        <div class="approver_id" style="font-weight: bold;">${i.userId}</div>
						      </div>
						    </div>
						  </c:forEach>
						
						  <!-- 빈 칸 채우기 (결재자가 4명 미만일 경우) -->
						  <c:forEach begin="${fn:length(vo.approvalLineVOs)+1}" end="4">
						    <div class="col-3 p-1">
						      <div class="approver-wrapper h-100" style="border: 1px solid #000;"></div>
						    </div>
						  </c:forEach>
					</div>
	      
	    		</div>
	
	    <!-- 부서/기안자/직책/작성일 -->
	    <div class="row m-0 p-0 text-center" style="border: 1px solid #000;">
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">기안자</div>
	      <div class="col-4 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000; height: 35px;" >
	        ${vo.writerName } ${vo.writerPosition }
	      </div>
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">부서</div>
	      <div class="col-4 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000; height: 35px;">
	        ${vo.writerDept }
	      </div>
	    </div>
	
	    <div class="row m-0 p-0 text-center" style="border: 1px solid #000; border-top: none;">
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">작성 시간</div>
	      <div class="col-4 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000; height: 35px;">
	        ${vo.writerTime }
	      </div>
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">처리 시간</div>
	      <div class="col-4 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000; height: 35px;">
	        ${vo.modifierTime }
	      </div>
	    </div>
	
	    <!-- 제목 -->
	    <div class="row m-0 p-0 text-center" style="border: 1px solid #000; border-top: none;">
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">제목</div>
	      <div class="col-10 d-flex align-items-center justify-content-center" style="height: 35px;">
	        ${vo.title }
	      </div>
	    </div>
	
	    <!-- 본문 내용, 스크립트 째로 content 프로퍼티에 집어 넣을 영역 -->
	    <div class="row m-0 p-0" style="height: 400px; border: 1px solid #000; border-top: none;">
	      
	      	<!-- ${vo.content }로 불러온다 -->
	      	
	      	
	      	
	      	
	      
	    </div>
	
		
	
	    <!-- 첨부파일 -->
	    	<div class="row m-0 p-0 text-center" style="border: 1px solid #000; border-top: none; height: 30px;">	
	    		붙임	
	    	</div>
	    	
	      <div class="row m-0 p-0 text-center" style="border: 1px solid #000; border-top: none; height: 100px;">
			  <c:forEach items="${vo.attachmentVOs}" var="i" varStatus="status">
			    <div class="col-3 p-1" style="border-right: 1px solid #000;">
			    	<div class="file-wrapper h-100" style="border: 1px solid #000;">
				      	${i.originName}			    	
			    	</div>
			    </div>
			  </c:forEach>
			
			  <!-- 빈 칸 채우기 (4개 미만일 경우) -->
			  <c:forEach begin="${fn:length(vo.attachmentVOs)+1}" end="4">
			    <div class="col-3 p-1">
			      <div class="file-wrapper h-100" style="border: 1px solid #000;"></div>
			    </div>
			  </c:forEach>
		</div>
	
	
	    <!-- 하단 버튼 -->
	    <div class="row m-0 p-0 mt-3">
	      <div class="col d-flex justify-content-end">
	        <button class="btn btn-outline-secondary me-2 mr-3">작성 완료</button>
	        <button class="btn btn-outline-secondary mr-3">나가기</button>
	      </div>
	    </div>
	
	  </div>
	  
	  
	  
	  
	  <div class="right-content" style="width: 150px; border: 1px solid #000;">
	  
	  		<div>양식</div>
			<div>${formVO.formId}</div>
			<br>
			
			<div>결재선</div>
			<c:forEach var="i" items="${approvers}">
				<div>${i.userId} ${i.name} ${i.position}</div>
			</c:forEach>
			<br>
	
			<div>참조선</div>
			<c:forEach var="i" items="${referrers}">
				<div>${i.userId} ${i.name} ${i.position}</div>
			</c:forEach>
			<br>		
			
			<form id="writeForm" action="/document/write" method="post">
			  <input type="text" name="formId" id="formId" value="${formVO.formId}" > <br><br>
			  <input type="text" name="writerId" id="writerId" value="${userVO.userId}" > <br><br>
			
			  <input type="text" name="title" id="title" placeholder="제목"> <br><br>
			  <input type="text" name="content" id="content" placeholder="내용"> <br><br>
			  <input type="file" name="attaches" id="attaches"> <br><br>
			  <!-- 처음 문서 작성 시 보낼 수 있는 결재선 정보 -->
			  <!-- 
			  	문서 번호, 사원 번호, 사원 이름, 사원 직급 결재 순번	
			   -->
			   
			   <!-- 처음 문서 작성 시 보낼 수 있는 참조선 정보 -->
			  <!-- 
			  	문서 번호, 사원 번호, 사원 이름, 사원 직급
			   -->
			   <button type="submit">작성 완료</button>
			</form>
	  
	  </div>
	  
  </div >
  

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