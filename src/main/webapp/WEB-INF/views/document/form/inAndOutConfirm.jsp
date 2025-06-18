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

<style>
.employee_approval {
      border-radius: 50rem;
      padding: 0.4em 0.75em;
      margin: 0.25em;
      background-color: #007bff;
      color: white;
      
      align-items: center;
    }

.employee_reference {
      border-radius: 50rem;
      padding: 0.4em 0.75em;
      margin: 0.25em;
      background-color: #007bff;
      color: white;
      
      align-items: center;
    }

</style>

</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				
			<div class="container-fluid">

			  <div class="whole-content row m-0 p-0" style="width: 1200px;">
			  	
			  	<div class="left-content" style="width: 100px; border: 1px solid #000;">
				  
				</div>
			  
				  <div class="middle-content" style="width: 800px; box-sizing: border-box;">  
				
				    <!-- 상단 영역 -->
				    <div class="row m-0 p-0" style="border: 1px solid #000;">	
				
				      <!-- 좌측: 번호 + 양식 이름 -->
				      <div class="top-left col-4 p-0 m-0" style="border-right: 1px solid #000;">
				        <div class="d-flex align-items-center justify-content-center" style="border-bottom: 1px solid #000; padding: 8px; height: 50px;">문서 번호 (${vo.documentId})</div>
				        <div class="d-flex align-items-center justify-content-center" style="padding: 8px; height: 200px;">
				        	${vo.formName}
				        <c:if test="${empty vo}">
					        ${formVO.name}				        
				        </c:if>				        
				        </div>
				      </div>
	
	
						<!-- 결재자 정보 -->
						<div class="top-right col-8 d-flex flex-column p-0 m-0" style="border-left: 1px solid #000;">
						
						  <!-- ✅ 제목 영역 -->
						  <div class="approver-title text-center fw-bold py-2" style="height: 50px; border-bottom: 1px solid #000; background-color: #f8f9fa;">
						    결재 라인
						  </div>
						
						  <!-- ✅ 결재자 목록 영역 -->
						  <div class="approver-list row g-0 p-0 m-0 flex-grow-1" style="height: 200px;">
						    <c:forEach items="${vo.approvalLineVOs}" var="i" varStatus="status">
						      <div class="col-3 p-1" >
						        <div class="approver-wrapper text-center h-100" style=" border: 1px solid #000;">
						          <div class="grade-title" style="border-bottom: 1px solid #000; font-weight: bold; width: 100%; height: 30px;">
						            ${i.userPosition}
						          </div>
						          <div class="stamp-box" style="border-top: 1px solid #000; border-bottom: 1px solid #000; width: 100%; height: 120px;">
						            <c:if test="${not empty i.signVO}">
						              <img src="/signs/${i.signVO.saveName}" style="width: 100%; height: 100%; object-fit: contain;" />
						            </c:if>
						          </div>
						          <div class="approver_name" style="border-bottom: 1px solid #000; font-weight: bold; width: 100%; height: 30px;">
						            ${i.userName}
						          </div>
						        </div>
						      </div>
						    </c:forEach>
						    
						    
						    <!-- 빈 칸 채우기 (결재자가 4명 미만일 경우) -->
						  <c:forEach begin="${fn:length(vo.approvalLineVOs)+1}" end="4"> <!-- visibility hidden으로 공간만 차지하고 보이지 않게 한다 -->
						    <div class="col-3 p-1" >
						      <div class="approver-wrapper text-center h-100" style=" border: 1px solid #000;">
						        <div class="grade-title" style="border-bottom: 1px solid #000; font-weight: bold; width: 100%; height: 30px;">직급</div>
						        <div class="stamp-box" style="border-top: 1px solid #000; border-bottom: 1px solid #000; width: 100%; height: 120px;">
						          직인칸
						        </div>
						        <div class="approver_name" style="border-bottom: 1px solid #000; font-weight: bold; width: 100%; height: 30px;">성명</div>
						        
						      </div>
						    </div>
						  </c:forEach>
						    
						    
						  </div>
						
						</div>
	
	
	
				      
						
						  
					
	      
	    		</div>
	
	    <!-- 부서/기안자/직책/작성일 -->
	    <div class="row m-0 p-0 text-center" style="border: 1px solid #000;">
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">기안자</div>
	      <div id="insert_writerName" class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000; height: 35px;" >
	        ${vo.writerName }
	        <c:if test="${empty vo}">
	        ${sessionScope.userVO.name}
	        </c:if>
	      </div>
	      <div id="insert_writerPosition" class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000; height: 35px;" >
	        ${vo.writerPosition }
	        <c:if test="${empty vo}">
	        ${sessionScope.userVO.position}
	        </c:if>
	      </div>
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">부서</div>
	      <div id="insert_dept" class="col-4 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000; height: 35px;">
	        ${vo.writerDept }
	        <c:if test="${empty vo}">
	        ${sessionScope.userVO.deptName}
	        </c:if>
	      </div>
	    </div>
	
	    <div class="row m-0 p-0 text-center" style="border: 1px solid #000; border-top: none;">
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">작성일</div>
	      <div id="insert_writeTime" class="col-4 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000; height: 35px;">
	        ${vo.writerTime }
	        <c:if test="${empty vo}">
	        ${formattedDate}
	        </c:if>
	      </div>
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">승인/반려일</div>
	      <div id="insert_handleTime" class="col-4 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000; height: 35px;">
	        ${vo.modifierTime}
	        <c:if test="${empty vo}">
	        ---
	        </c:if>
	      </div>
	    </div>
	
	    <!-- 제목 -->
	    <div class="row m-0 p-0 text-center" style="border: 1px solid #000; border-top: none;">
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">제목</div>
	      
	      <c:if test="${empty vo}">
		      	<div id="insert_title" class="col-10 d-flex align-items-center justify-content-center" style="height: 35px;" contenteditable="true">
		        
		      	</div>
	      </c:if>
	      
	      <c:if test="${not empty vo}">
		      <div id="insert_title" class="col-10 d-flex align-items-center justify-content-center" style="height: 35px;">
		        ${vo.title}
		      </div>	      
	      </c:if>
	    </div>
	
	    <!-- 본문 내용, 스크립트 째로 content 프로퍼티에 집어 넣을 영역 -->
	    <div id="insert_content" data-vo-check="${vo.documentId}" class="m-0 p-0" style="min-height: 400px; border: 1px solid #000; border-top: none;">
	      
	      	<div class="col d-flex justify-content-center align-items-center">
	      	<!-- ${vo.content }로 불러온다 -->
	      	
	      	<table border="1" cellspacing="0" cellpadding="5" style="border-collapse: collapse; width: 100%; text-align: center;">
			  <thead>
			    <tr style="background-color: #d9edf7;">
			      <th colspan="7" style="padding: 10px; font-weight: bold;">입 출 고 내 역</th>
			      <!-- 입출고 상태 (입고, 출고), 상품명, 상품 갯수 -->
			    </tr>
			    <tr>
			      <th>일시</th>
			      <th>분류</th>
			      <th>상세</th>
			      <th>입고 수량</th>
			      <th>출고 수량</th>
			      <th>현재 재고</th>
			      <th>비고</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach begin="1" end="5">
				    <tr style="height: 35px;">
				    	<td class="td_date tds" style="width: 15%;"></td>
				      	<td class="td_kind tds" style="width: 15%;"></td>
				      	<td class="td_name tds" style="width: 15%;"></td>
				      	<td class="td_inbound tds" style="width: 15%;"></td>
				      	<td class="td_outbound tds" style="width: 15%;"></td>
				      	<td class="td_remain tds" style="width: 15%;"></td>
				      	<td class="td_etc tds" style="width: 10%;"></td>		      
				    </tr>			  	
			  	</c:forEach>
			    
			    
			  </tbody>
			</table>
			
			
		  </div>
	      	
	      
	      <div class="col">
	      	<div class="m-0 p-0 text-center" style="border: 1px solid #000; border-top: none; height: 130px;">
	    		<div>상기 입출고 내역을 제출하고자 합니다.</div>
	    		<div>${formattedDate}</div>
	    	</div>
	      </div>	
	      	
	      	
	      
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
	        <button id="btn_complete" class="btn btn-outline-secondary me-2 mr-3">작성 완료</button>
	        <button class="btn btn-outline-secondary mr-3">나가기</button>
	      </div>
	    </div>
	
	  </div>
	  
	  
	  
	  
	  <div class="right-content" style="width: 250px; border: 1px solid #000;">
	  
	  		<div>양식 데이타</div>
			<div>${formVO.formId}</div>
			<br>
			
			<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" id="diagram">
				조직도 조회    
    		</button>
			<br>
			
			<button id="btn_resetApprovaers">결재선 초기화</button>
			<div id="box_approvers" class="" style="border: 1px solid black;"> 결재자 목록
				<c:forEach var="i" items="${approvers}">
					<div>${i.userId} ${i.name} ${i.position}</div>
				</c:forEach>			
			</div>
			<br>
	
			<button id="btn_resetReferrers">참조선 초기화</button>
			<div id="box_referrers" style="border: 1px solid black;"> 참조자 목록
				<c:forEach var="i" items="${referrers}">
					<div>${i.userId} ${i.name} ${i.position}</div>
				</c:forEach>			
			</div>
			<br>		
			
			<div id="id_reject_wrapper">
				<div style="border: 1px solid black;">반려 제목</div>
				<div style="border: 1px solid black;">반려 내용</div>			
			</div>
			
			
			<!-- Controller로 데이터 날릴 폼 -->
			<form id="form_document" method="post">
				<input id="input_formId" name="formId" type="hidden" value="${formVO.formId}">
				
				<input id="input_writerId" name="writerId" type="hidden" value="${sessionScope.userVO.userId}">
				<input id="input_writerName" name="writerName" type="hidden" value="${sessionScope.userVO.name}">
				<input id="input_writerPosition" name="writerPosition" type="hidden" value="${sessionScope.userVO.position}">
				<input id="input_writerDept" name="writerDept" type="hidden" value="${sessionScope.userVO.deptName}">
								
				<input id="input_title" name="title" type="hidden" value="">
				<input id="input_content" name="content" type="hidden" value="">
				
				<input id="input_files" name="files" type="hidden">
				
				<input id="input_approvers" name="approvers" type="hidden">
				
				<input id="input_referrers" name="referrers" type="hidden">
				
 
				
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
	<c:import url="/WEB-INF/views/templates/ocModal.jsp"></c:import>
	
	<!-- End Wrapper -->
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
	
	<script src="/js/document/writeJs.js"></script>
	
</body>
</html>