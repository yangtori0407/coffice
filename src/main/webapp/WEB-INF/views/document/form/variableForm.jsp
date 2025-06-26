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

#id_signList {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap; /* 넘치면 다음 줄로 */
    gap: 20px;        /* 항목 사이 간격 */
    height: auto;
    border: solid black 1px;
    padding: 10px;
  }

  .signWrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 130px;
  }

  .file-wrapper {
	display: flex;
	gap: 10px;
	align-items: center;
	border: 1px solid black;
	padding: 5px;
	margin-bottom: 5px;
  }


  .exist-files:hover {
  	cursor: pointer;
	background-color: rgb(188, 218, 230);
  }	
  
  .exist-files:active {
  	
	background-color: rgb(170, 176, 179);
  }	

</style>

</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/document/modal/signModal.jsp"></c:import>
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
				        <div class="d-flex align-items-center justify-content-center" style="border-bottom: 1px solid #000; padding: 8px; height: 50px;">문서 번호 (${docuVO.documentId})</div>
				        <div class="d-flex align-items-center justify-content-center" style="padding: 8px; height: 200px;">				        	
					        <c:if test="${empty docuVO}">
						        ${formVO.name}
					        </c:if>
					        ${docuVO.formName }
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
						    <c:forEach items="${docuVO.approvalLineVOs}" var="i" varStatus="status">
						      <div class="col-3 p-1" >
						        <div class="approver-wrapper text-center h-100" style=" border: 1px solid #000;">
						          <div class="grade-title" style="border-bottom: 1px solid #000; font-weight: bold; width: 100%; height: 30px;">
						            ${i.userPosition}
						          </div>
						          <div class="stamp-box" style="border-top: 1px solid #000; border-bottom: 1px solid #000; width: 100%; height: 120px;">
						            <c:if test="${not empty i.signVO}">
						              <img src="/signs/${i.signVO.saveName}" style="width: 100%; height: 100%; object-fit: contain;" />
						            </c:if>
						            
						            <c:if test="${i.status eq '반려' && docuVO.status eq '반려'}">
						            	<img src="/signs/banryeo.png" style="width: 100%; height: 100%; object-fit: contain;" />
						            </c:if>
						          </div>
						          <div class="approver_name" style="border-bottom: 1px solid #000; font-weight: bold; width: 100%; height: 30px;">
						            ${i.userName}
						          </div>
						        </div>
						      </div>
						    </c:forEach>
						    
						    
						    <!-- 빈 칸 채우기 (결재자가 4명 미만일 경우) -->
						  <c:forEach begin="${fn:length(docuVO.approvalLineVOs)+1}" end="4"> <!-- visibility hidden으로 공간만 차지하고 보이지 않게 한다 -->
						    <div class="col-3 p-1" >
						      <div class="approver-wrapper text-center h-100" style=" border: 1px solid #000; visibility: hidden" >
						        <div class="grade-title" style="border-bottom: 1px solid #000; font-weight: bold; width: 100%; height: 30px;"></div>
						        <div class="stamp-box" style="border-top: 1px solid #000; border-bottom: 1px solid #000; width: 100%; height: 120px;">
						          
						        </div>
						        <div class="approver_name" style="border-bottom: 1px solid #000; font-weight: bold; width: 100%; height: 30px;"></div>
						        
						      </div>
						    </div>
						  </c:forEach>
						    
						    
						  </div>
						
						</div>
	
	
	
				      
						
						  
					
	      
	    		</div>
	
	    <!-- 부서/기안자/직책/작성일 -->
	    <div class="row m-0 p-0 text-center" style="border: 1px solid #000;">
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">기안자</div>
	      <div id="insert_writerName" class="col-2 d-flex align-items-center justify-content-center informations" style="border-right: 1px solid #000; height: 35px;" contenteditable="">
		        ${docuVO.writerName }
		        <c:if test="${empty docuVO}">
		        ${sessionScope.user.name}
		        </c:if>
	      </div>
	      <div id="insert_writerPosition" class="col-2 d-flex align-items-center justify-content-center informations" style="border-right: 1px solid #000; height: 35px;" contenteditable="">
		        ${docuVO.writerPosition }
		        <c:if test="${empty docuVO}">
		        ${sessionScope.user.position}
		        </c:if>
	      </div>
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">부서</div>
	      <div id="insert_writerDept" class="col-4 d-flex align-items-center justify-content-center informations" style="border-right: 1px solid #000; height: 35px;" contenteditable="">
		        ${docuVO.writerDept }
		        <c:if test="${empty docuVO}">
		        ${sessionScope.user.deptName}
		        </c:if>
	      </div>
	    </div>
	
	    <div class="row m-0 p-0 text-center" style="border: 1px solid #000; border-top: none;">
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">작성일</div>
	      <div id="insert_writeTime" class="col-4 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000; height: 35px;">
		        <c:if test="${not empty docuVO && docuVO.status ne '임시저장'}">
		        	${docuVO.writerTime}
		        </c:if>
		        
		        <c:if test="${empty docuVO || docuVO.status eq '임시저장'}">
		        	${fakeToday}
		        </c:if>
	      </div>
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">처리일</div>
	      <div id="insert_handleTime" class="col-4 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000; height: 35px;">
		        
		        <c:if test="${not empty docuVO && docuVO.status ne '임시저장'}">
		        	${docuVO.modifierTime}
		        </c:if>
		        
		        <c:if test="${empty docuVO || docuVO.status eq '임시저장' }">
		        	---
		        </c:if>
	      </div>
	    </div>
	
	    <!-- 제목 -->
	    <div class="row m-0 p-0 text-center" style="border: 1px solid #000; border-top: none;">
	      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid #000;">제목</div>
	      
		      	<div id="insert_title" class="col-10 d-flex align-items-center justify-content-center informations" style="height: 35px;" contenteditable="">
			      <c:if test="${empty docuVO}">
				        
			      </c:if>
			      
			      <c:if test="${not empty docuVO}">
				        ${docuVO.title}	      
			      </c:if>
		      	</div>
	          	      
	    </div>
	
	    <!-- 본문 내용, 스크립트 째로 content 프로퍼티에 집어 넣을 영역 -->	    
	    <div id="insert_content" data-vo-check="${docuVO.status}" class="m-0 p-0" style="min-height: 400px; border: 1px solid #000; border-top: none;">
	      	
 	      	<c:if test="${empty docuVO}">
	      		${formVO.formFrame}
	      	</c:if>	      	
	      		${docuVO.content}
	      	 
			
			
	      	
	    </div>
	
		
	
	    <!-- 첨부파일 -->
	    <div class="row m-0 p-0 align-items-center" style="border: 1px solid #000; border-top: none; height: 40px;">
		  <div class="col-auto d-flex align-items-center ps-2">
		    <span class="me-3" style="margin-right: 10px;">첨부 목록</span>
		    
		    <c:choose>
				<c:when test="${(empty docuVO && isWritePage eq 1) || docuVO.status eq '임시저장' }">
					<button id="uploadBtn" class="btn btn-sm btn-outline-secondary">내 PC</button>
				</c:when>
				
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>		    
		    
		    
		    
		    <input type="file" id="fake_input_files" style="display: none;" multiple>
		  </div>
		</div>
	    	
	    <div class="row m-0 p-0 text-center" style="border: 1px solid #000; border-top: none; min-height: 100px;">
			  
		    <div id="fileList" class="col-12 p-1" style="border-right: 1px solid #000;">
		    	<c:forEach items="${docuVO.attachmentVOs}" var="i">
		    		<div class="file-wrapper">
		    		
		    			<c:choose>
							<c:when test="${(empty docuVO && isWritePage eq 1) || docuVO.status eq '임시저장' }">
								<button class="exist-remover">X</button>
							</c:when>
							
							<c:otherwise>
								
							</c:otherwise>
						</c:choose>	
		    			
		    			<div class="exist-files" data-file-num="${i.fileNum}">
		    				${i.originName} [다운로드]
		    			</div>
		    			
		    		</div>
		    	</c:forEach>
		    </div>
			  
		</div>
	
	
	    <!-- 하단 버튼 -->
	    <div class="row m-0 p-0 mt-3">
	      <div class="col d-flex justify-content-end">
	        <a class="dropdown-item" href="#" data-toggle="modal" data-target="#signMenu">
                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                결재하기
            </a>
            <a class="dropdown-item" href="#" data-toggle="modal" data-target="#writeRejectReason">
                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                반려하기
            </a>
            
            <!-- 접속한 사람이 작성자 본인이고, 문서가 진행중이고, 스텝이 1이어야한다(미처리 상태) -->
            <c:if test="${sessionScope.user.userId eq docuVO.writerId && docuVO.status eq '진행중' && docuVO.currentStep eq '1'}">
            	<button id="btn_getBack" class="btn btn-outline-secondary me-2 mr-3" 
            	data-document-id="${docuVO.documentId}">문서 회수</button>
            </c:if>
            <c:if test="${docuVO.status eq '임시저장' }">
            	<button id="btn_deletetemp" class="btn btn-outline-secondary me-2 mr-3"
            	data-document-id="${docuVO.documentId}">작성 취소</button>
            </c:if>
			<button id="btn_temporary" class="btn btn-outline-secondary me-2 mr-3">임시 저장</button>
	        <button id="btn_complete" class="btn btn-outline-secondary me-2 mr-3">작성 완료</button>
	        <button id="btn_cancle" class="btn btn-outline-secondary mr-3">나가기</button>
	      </div>
	      
	    </div>
	
	  </div>
	  
	  
	  
	  
	  <div class="right-content" style="width: 150px; border: 1px solid #000;">
	  
	  		
			<c:choose>
				<c:when test="${(empty docuVO && isWritePage eq 1) || docuVO.status eq '임시저장' }">
					<div class="mt-5 mb-5" style=" min-height: 50px;">
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" id="diagram">
							사원 불러오기    
			    		</button>
					</div>
				</c:when>
				
				<c:otherwise>
					<div class="mt-5 mb-5" style=" min-height: 50px;">
					
					</div>
				</c:otherwise>
			</c:choose>
			
			<button id="btn_resetApprovaers">결재선 초기화</button>
			<div id="box_approvers" class="mb-5" style="border: 1px solid black; min-height: 100px;"> 결재자 목록
								
				<c:forEach var="i" items="${docuVO.approvalLineVOs}">
					<div class="employee_approval" data-selected-id="${i.userId}" data-selected-name="${i.userName}" data-selected-position="${i.userPosition}">
						${i.userName} ${i.userPosition}
					</div>
				</c:forEach>
							
			</div>
			
	
			<button id="btn_resetReferrers">참조선 초기화</button>
			<div id="box_referrers" class="mb-5" style="border: 1px solid black; min-height: 100px;"> 참조자 목록
								
				<c:forEach var="i" items="${docuVO.referenceLineVOs}">
					<div class="employee_reference" data-selected-id="${i.userId}" data-selected-name="${i.userName}" data-selected-position="${i.userPosition}">
						${i.userName} ${i.userPosition}
					</div>
				</c:forEach>
				
			</div>
					
			
			<c:if test="${docuVO.status eq '반려'}">
				<div id="id_reject_wrapper">
					<button class="btn btn-info" type="button" id="id_view_reject" 
					data-toggle="modal" data-target="#rejectReasonModal">반려 사유 보기</button>
				</div>
			</c:if>
			
			<!-- Controller로 데이터 날릴 폼 -->
			<form id="form_document" method="post" action="/document/write" enctype="multipart/form-data">
				<input id="input_documentId" name="documentId" type="hidden" value="${docuVO.documentId}">
				<input id="input_formId" name="formId" type="hidden" value="${formVO.formId}">
				
				<input id="input_writerId" name="writerId" type="hidden" value="${sessionScope.user.userId}">
				<input id="input_writerName" name="writerName" type="hidden" value="">
				<input id="input_writerPosition" name="writerPosition" type="hidden" value="">
				<input id="input_writerDept" name="writerDept" type="hidden" value="">
								
				<input id="input_title" name="title" type="hidden" value="">
				<input id="input_content" name="content" type="hidden" value="">
				
				
				
				<input id="input_approvers" name="approvers" type="hidden">
				
				<input id="input_referrers" name="referrers" type="hidden">
				
				<input id="input_docuStatus" name="status" type="hidden">
 
				
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
	<c:import url="/WEB-INF/views/document/modal/rejectReasonModal.jsp"></c:import>
	
	
	<!-- End Wrapper -->
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
	
	<script src="/js/document/writeJs.js"></script>
	<script src="/js/document/signTool.js"></script>
	<script src="/js/document/toReject.js"></script>
	<script src="/js/document/toTemporary.js"></script>
	
	
</body>
</html>