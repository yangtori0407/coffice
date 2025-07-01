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
      
      
      align-items: center;
    }

.employee_reference {
      border-radius: 50rem;
      padding: 0.4em 0.75em;
      margin: 0.25em;
      background-color: #007bff;
      
      
      align-items: center;
    }

#id_signList {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap; /* 넘치면 다음 줄로 */
    gap: 20px;        /* 항목 사이 간격 */
    height: auto;
    border: solid  1px;
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
			
			<div class="card shadow mb-4 mother-card" style="min-width: 1300px;" >
			
				<div class="mother-content  d-flex justify-content-center align-items-start" style="min-width: 1300px; height: 1150px;">
				
				  	
				  
					  <div class="middle-content" style="width:1000px; box-sizing: border-box;">  
					
					
					    <!-- 상단 영역 -->
					    
					    <div class="card mt-2 mb-2">
					    
					    <div class="row m-0 p-0" >	
					
					      <!-- 좌측: 번호 + 양식 이름 -->
					      <div class="top-left col-4 p-0 m-0" >
					        <div class="card-header d-flex align-items-center justify-content-center" style=" padding: 8px; height: 50px; font-size: large;">문서 번호 (${docuVO.documentId})</div>
					        <div class="d-flex align-items-center justify-content-center" style="padding: 8px; height: 200px; font-size: x-large;">				        	
						        <c:if test="${empty docuVO}">
							        ${formVO.name}
						        </c:if>
						        ${docuVO.formName }
					        </div>
					      </div>
		
		
							<!-- 결재자 정보 -->
							<div class="top-right col-8 d-flex flex-column p-0 m-0" style="border-left: 1px solid lightgray;">
							
							  <!-- ✅ 제목 영역 -->
							  <div class="card-header approver-title d-flex align-items-center justify-content-center" style="height: 50px; font-size: large;">
							    결재 라인
							  </div>
							
							  <!-- ✅ 결재자 목록 영역 -->
							  <div class="approver-list row g-0 p-0 m-0 flex-grow-1 d-flex align-items-center justify-content-center" style="height: 200px;">
							    <c:forEach items="${docuVO.approvalLineVOs}" var="i" varStatus="status">
							      <div class="col-3 p-1" >
							        <div class="approver-wrapper text-center" style=" border: 1px solid silver;">
							          <div class="grade-title d-flex align-items-center justify-content-center" style="font-weight: bold; width: 100%; height: 30px;">
							            ${i.userPosition}
							          </div>
							          <div class="stamp-box" style="border-top: 1px solid silver; border-bottom: 1px solid silver; width: 100%; height: 120px;">
							            <c:if test="${not empty i.signVO}">
							              <img src="/signs/${i.signVO.saveName}" style="width: 100%; height: 100%; object-fit: contain;" />
							            </c:if>
							            
							            <c:if test="${i.status eq '반려' && docuVO.status eq '반려'}">
							            	<img src="/signs/banryeo.png" style="width: 100%; height: 100%; object-fit: contain;" />
							            </c:if>
							          </div>
							          <div class="approver_name d-flex align-items-center justify-content-center" style=" font-weight: bold; width: 100%; height: 30px;">
							            ${i.userName}
							          </div>
							        </div>
							      </div>
							    </c:forEach>
							    
							    
							    <!-- 빈 칸 채우기 (결재자가 4명 미만일 경우) -->
							  <c:forEach begin="${fn:length(docuVO.approvalLineVOs)+1}" end="4"> <!-- visibility hidden으로 공간만 차지하고 보이지 않게 한다 -->
							    <div class="col-3 p-1" >
							      <div class="approver-wrapper text-center" style=" border: 1px solid silver; visibility: hidden" >
							        <div class="grade-title d-flex align-items-center justify-content-center" style="font-weight: bold; width: 100%; height: 30px;"></div>
							        <div class="stamp-box" style="border-top: 1px solid silver; border-bottom: 1px solid silver; width: 100%; height: 120px;"></div>
							        <div class="approver_name d-flex align-items-center justify-content-center" style="font-weight: bold; width: 100%; height: 30px;"></div>
							        
							      </div>
							    </div>
							  </c:forEach>
							    
							    
							  </div>
							
							</div>
		
		    		</div>
		    		
		    		</div>
		
			    <!-- 부서/기안자/직책/작성일 -->
			    
			    <div class="card mb-2" style="border: none;">
			    
			    <div class="row m-0 p-0 text-center mb-1" style="border: 1px solid lightgray;">
			      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid lightgray; background-color: #f8f9fa; font-size: large;">기안자</div>
			      <div id="insert_writerName" class="col-2 d-flex align-items-center justify-content-center informations" style="border-right: 1px solid lightgray; height: 35px;" contenteditable="">
				        ${docuVO.writerName }
				        <c:if test="${empty docuVO}">
				        ${sessionScope.user.name}
				        </c:if>
			      </div>
			      <div id="insert_writerPosition" class="col-2 d-flex align-items-center justify-content-center informations" style="border-right: 1px solid lightgray; height: 35px;" contenteditable="">
				        ${docuVO.writerPosition }
				        <c:if test="${empty docuVO}">
				        ${sessionScope.user.position}
				        </c:if>
			      </div>
			      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid lightgray; background-color: #f8f9fa; font-size: large;">부서</div>
			      <div id="insert_writerDept" class="col-4 d-flex align-items-center justify-content-center informations" style=" height: 35px;" contenteditable="">
				        ${docuVO.writerDept }
				        <c:if test="${empty docuVO}">
				        ${sessionScope.user.deptName}
				        </c:if>
			      </div>
			    </div>
			
			    <div class="row m-0 p-0 text-center mb-1" style="border: 1px solid lightgray; ">
			      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid lightgray; background-color: #f8f9fa; font-size: large;" >작성일</div>
			      <div id="insert_writeTime" class="col-4 d-flex align-items-center justify-content-center" style="border-right: 1px solid lightgray; height: 35px;">
				        <c:if test="${not empty docuVO && docuVO.status ne '임시저장'}">
				        	${docuVO.writerTime}
				        </c:if>
				        
				        <c:if test="${empty docuVO || docuVO.status eq '임시저장'}">
				        	${fakeToday}
				        </c:if>
			      </div>
			      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid lightgray; background-color: #f8f9fa; font-size: large;">처리일</div>
			      <div id="insert_handleTime" class="col-4 d-flex align-items-center justify-content-center" style=" height: 35px;">
				        
				        <c:if test="${not empty docuVO && docuVO.status ne '임시저장'}">
				        	${docuVO.modifierTime}
				        </c:if>
				        
				        <c:if test="${empty docuVO || docuVO.status eq '임시저장' }">
				        	---
				        </c:if>
			      </div>
			    </div>
			
			    <!-- 제목 -->
			    <div class="row m-0 p-0 text-center" style="border: 1px solid lightgray;">
			      <div class="col-2 d-flex align-items-center justify-content-center" style="border-right: 1px solid lightgray; background-color: #f8f9fa; font-size: large;">제목</div>
			      
				      	<div id="insert_title" class="col-10 d-flex align-items-center justify-content-center informations" style="height: 35px;" contenteditable="">
					      <c:if test="${empty docuVO}">
						        
					      </c:if>
					      
					      <c:if test="${not empty docuVO}">
						        ${docuVO.title}	      
					      </c:if>
				      	</div>
			          	      
			    </div>
			    
			    </div>
			
			
			
			    <!-- 본문 내용, 스크립트 째로 content 프로퍼티에 집어 넣을 영역 -->
			    
			    <div class="card mb-2">
			    	    
			    <div id="insert_content" data-vo-check="${docuVO.status}" class="m-0 p-0" style="min-height: 400px; ">
			      	
		 	      	<c:if test="${empty docuVO}">
			      		${formVO.formFrame}
			      	</c:if>	      	
			      		${docuVO.content}
			      	 
					
					
			      	
			    </div>
			    
				</div>
				
			
			    <!-- 첨부파일 -->
			    
			    <div class="card mb-2">
			    
			    <div class="card-header row m-0 p-0 align-items-center" style=" height: 40px; background-color: #f8f9fa;">
				  <div class="col-auto d-flex align-items-center ps-2">
				    <span class="me-3" style="margin-right: 10px; font-size: large;">첨부 목록</span>
				    
				    <c:choose>
						<c:when test="${(empty docuVO && isWritePage eq 1) || docuVO.status eq '임시저장' }">
							<button id="uploadBtn" class="btn btn-sm btn-primary">내 PC</button>
						</c:when>
						
						<c:otherwise>
							
						</c:otherwise>
					</c:choose>		    
				    
				    
				    
				    <input type="file" id="fake_input_files" style="display: none;" multiple>
				  </div>
				</div>
			    	
			    <div class="row m-0 p-0 text-center" style="min-height: 100px;">
					  
				    <div id="fileList" class="col-12 p-1">
				    	<c:forEach items="${docuVO.attachmentVOs}" var="i">
				    		<div class="file-wrapper">
				    		
				    			<c:choose>
									<c:when test="${(empty docuVO && isWritePage eq 1) || docuVO.status eq '임시저장' }">
										<button class="exist-remover" style="color: gray; border: none;">X</button>
									</c:when>
									
									<c:otherwise>
										
									</c:otherwise>
								</c:choose>	
				    			
				    			<div class="exist-files" data-file-num="${i.fileNum}" style="font-size: large;">
				    				${i.originName} [다운로드]
				    			</div>
				    			
				    		</div>
				    	</c:forEach>
				    </div>
					  
				</div>
			
				</div>
			
			    <!-- 하단 버튼 -->
			    
			    <div class="card mb-2">
			    
			    <div class="row m-0 p-0 mt-3">
			      <div class="col d-flex justify-content-end mb-3">
			      
			      <!-- 문서의 결재순서와 결재권자의 결재순서가 일치하고 접속자가 그 결재권자와 uerId가 일치하면 처리 버튼을 보여준다 -->	      
			      <c:forEach items="${docuVO.approvalLineVOs}" var="i">
			      	<c:if test="${i.stepOrder eq docuVO.currentStep && i.userId eq sessionScope.user.userId}">
			            <button class="btn btn-success mr-3 " data-toggle="modal" data-target="#writeRejectReason">                
			                반려하기
			            </button>
				        <button class="btn btn-primary mr-3 " data-toggle="modal" data-target="#signMenu">                
			                결재하기
			            </button>
			      	
			      	</c:if>
			      </c:forEach>
			      	
			      
		            
		            <!-- 접속한 사람이 작성자 본인이고, 문서가 진행중이고, 스텝이 1이어야한다(미처리 상태) -->
		            <c:if test="${sessionScope.user.userId eq docuVO.writerId && docuVO.status eq '진행중' && docuVO.currentStep eq '1'}">
		            	<button id="btn_getBack" class="btn btn-primary me-2 mr-3" 
		            	data-document-id="${docuVO.documentId}">문서 회수</button>
		            </c:if>
		            <c:if test="${docuVO.status eq '임시저장' }">
		            	<button id="btn_deletetemp" class="btn btn-danger me-2 mr-3"
		            	data-document-id="${docuVO.documentId}">작성 취소</button>
		            </c:if>
		            
		            <!-- 최초 작성 페이지이거나 임시저장 문서를 불러온 상황이다 -->
		            <c:choose>
			            <c:when test="${(empty docuVO && isWritePage eq 1) || docuVO.status eq '임시저장'}">
							<button id="btn_temporary" class="btn btn-success me-2 mr-3 ">임시 저장</button>
					        <button id="btn_complete" class="btn btn-primary me-2 mr-3" >작성 완료</button>            
			            </c:when>
			            <c:otherwise>
			            
			            </c:otherwise>            
		            </c:choose>
			        
			        <c:if test="${not empty docuVO}">
			        	<button id="btn_tolist" class="btn btn-secondary me-2 mr-3" 
			        	onclick="location.href='/document/list/${docuKind}'" >목록 가기</button>
			        </c:if>
			        
			      </div>
			      
			    </div>
			    
			    </div>
			    
			    
			
			  </div>
			  <!-- middle-content의 끝 -->
		  
		  
		  
		  
		  
			  <div class="card right-content mt-2 mb-2 ml-3" style="width:250px; height: 98%;">
			  
			  		<div class="right-1" style="height: 50px;">
			  		
			  		</div>
			  		
			  		<div class="right-2" style="height: 100px;">
			  			<c:choose>
							<c:when test="${(empty docuVO && isWritePage eq 1) || docuVO.status eq '임시저장' }">
								<div class="btn btn-primary d-flex align-items-center justify-content-center ml-3 font-weight-bold" style="height: 90%; width: 90%; font-size: large;" 
								data-toggle="modal" data-target="#exampleModal" id="diagram">								
										사원 불러오기
								</div>
							</c:when>
							
							<c:otherwise>
								<div class="btn btn-secondary d-flex align-items-center justify-content-center ml-3 font-weight-bold" style="height: 90%; width: 90%; font-size: large;">								
										사원 불러오기
								</div>
							</c:otherwise>
						</c:choose>
			  		</div>
			  		
			  		<div class="card  right-3 ml-3 mt-3 mb-5 d-flex flex-column align-items-center" style="height: 300px; width: 90%;">
			  			<div class="card-header d-flex align-items-center justify-content-center text-primary font-weight-bold" style=" height: 20%; width: 100%; font-size:larger;">
			  				결재자 목록
			  			</div>
			  			<div id="box_approvers" class="d-flex flex-column align-items-center" style="overflow-y: auto;  height: 80%; width: 100%;">									
							<c:forEach var="i" items="${docuVO.approvalLineVOs}">
								<div class="employee_approval mt-2 mb-2 d-flex align-items-center justify-content-center" style="width: 80%; color: white;" 
								data-selected-id="${i.userId}" data-selected-name="${i.userName}" data-selected-position="${i.userPosition}">
									${i.userName} ${i.userPosition}
								</div>
							</c:forEach>								
						</div>
						
			  		</div>
			  		
			  		<div class="card  right-4 ml-3 d-flex flex-column align-items-center" style="height: 300px; width: 90%;">
			  			<div class="card-header d-flex align-items-center justify-content-center text-primary font-weight-bold" style=" height: 20%; width: 100%; font-size:larger;">
			  				참조자 목록
			  			</div>
			  			<div id="box_referrers" class="d-flex flex-column align-items-center" style="overflow-y: auto;  height: 80%; width: 100%">									
							<c:forEach var="i" items="${docuVO.referenceLineVOs}">
								<div class="employee_reference mt-2 mb-2 d-flex align-items-center justify-content-center" style="width: 80%; color: white;" 
								data-selected-id="${i.userId}" data-selected-name="${i.userName}" data-selected-position="${i.userPosition}">
									${i.userName} ${i.userPosition}
								</div>
							</c:forEach>					
						</div>
						
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
			  <!-- right content의 끝 -->
			  
			  
			</div>
				  	
	  
	  
	</div>
	
  

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
	
	<script>
		//첨부 파일 다운로드
		const exist_files = document.getElementsByClassName("exist-files");

		for(let file of exist_files){
			file.addEventListener("click", function(){
				let fileNum = file.dataset.fileNum;
				
				// form 요소 생성
				const form = document.createElement("form");
				form.method = "post";
				form.action = "/document/filedown";
		
				// hidden input 추가
				const input = document.createElement("input");
				input.type = "hidden";
				input.name = "fileNum";
				input.value = fileNum;
				form.appendChild(input);
				
				// body에 form 추가 후 submit
				document.body.appendChild(form);
				form.submit();

			})

		}
		
		
		
		
	</script>
	<script src="/js/document/writeJs.js"></script>
	<script src="/js/document/signTool.js"></script>
	<script src="/js/document/toReject.js"></script>
	<script src="/js/document/toTemporary.js"></script>
	
	
</body>
</html>