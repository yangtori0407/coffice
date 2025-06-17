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
<style>
	.boxs {
		border: 1px solid black;
	}
	
	#id_approvalLine {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap; /* 넘치면 다음 줄로 */
    gap: 20px;        /* 항목 사이 간격 */
    height: auto;
    border: solid black 1px;
    padding: 10px;
  }

  .approver-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 130px;
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

  
	
</style>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">

					<!-- contents 내용 -->
					<div>
					document Detail 페이지
					</div>
					<br>
					
					<div class="col-6 mb-3" id="id_approvalLine_wrapper">
						<div id="id_approvalLine" style="height: 200px; border: solid black 1px;">
						
							<c:forEach items="${vo.approvalLineVOs}" var="i">
								<div class="approver-wrapper" style="margin-bottom: 10px;">
									<div class="grade-title" style="border: 1px solid gray; font-weight: bold;">${i.userPosition }</div>
									<div class="stamp-box" data-user-id="" style="border: 1px dashed gray; width: 120px; height: 120px;">								
										<!-- 조건 : 결재에 사용한 사인이 있다면 사인 이미지를 출력한다-->
										<c:if test="${not empty i.signVO}"> 
											<img src="/signs/${i.signVO.saveName}" style="width:100%; height:100%; object-fit:contain;" />
										</c:if>	
									</div>
									<div class="approver_name" style="border: 1px solid gray; font-weight: bold;">${i.userName }</div>
									<div class="approver_id" style="border: 1px solid gray; font-weight: bold;">${i.userId}</div>
								</div>						
							</c:forEach>						
							
						</div>
					</div>	
						
					
					<div class="boxs"> 문서 정보 및 작성자 정보
						<div>${vo.documentId }</div>
						<div>${vo.formName }</div>
						<div>${vo.title }</div>
						<div>${vo.content }</div>
						<div>${vo.writerId }</div>
						<div>${vo.writerName }</div>
						<div>${vo.writerPosition }</div>
						<div>${vo.writerDept }</div>
						<div>${vo.writerTime }</div>
						<div>${vo.currentStep }</div>
						<div>${vo.status }</div>
					</div>
					<br>
					
					<div class="boxs"> 수정자 정보
						<div>${vo.modifierId }</div>
						<div>${vo.modifierName }</div>
						<div>${vo.modifierPosition }</div>
						<div>${vo.modifierDept }</div>
						<div>${vo.modifierTime }</div>
					</div>
					<br>
					
					<div class="boxs"> 첨부파일
						<c:forEach items="${vo.attachmentVOs}" var="i">
							<div>${i.documentId}</div>
							<div>${i.fileNum}</div>
							<div>${i.originName}</div>
							<div>${i.saveName}</div><br>
						</c:forEach>
					</div>
					<br>
					
					<div class="boxs"> 결재선
						<c:forEach items="${vo.approvalLineVOs}" var="i">
							<div>${i.documentId}</div>
							<div class="appIds" data-user-id="${i.userId}">${i.userId}</div>
							<div>${i.userName}</div>
							<div>${i.userPosition}</div>
							<div>${i.signId}</div>
							<div>${i.stepOrder}</div>
							<div>${i.status}</div>
							<div>${i.handlingTime}</div><br>
							<div>${i.rejectReason}</div>
						</c:forEach>
					</div>
					<br>
					
					<div class="boxs"> 참조선
						<c:forEach items="${vo.referenceLineVOs}" var="i">
							<div>${i.documentId}</div>
							<div>${i.userId}</div>
							<div>${i.userName}</div>
							<div>${i.userPosition}</div>
							<br>
						</c:forEach>
					</div>
					<br>
					
					
					<a class="dropdown-item" href="#" data-toggle="modal" data-target="#id-sign-manager">
		                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
		                결재하기
		            </a>
					
					<!-- 결재 및 직인사용 시 넘길 데이터 Form -->
					<form id="proceedForm" action="/document/proceed" method="post">
						<input type="hidden" name="documentId" id="documentId" value="${vo.documentId}">
						<input type="hidden" name="signId" id="signId">
						<input type="hidden" name="userId" id="userId" value="${sessionScope.userVO.userId}">
					</form>


					<!-- 직인 메뉴 Modal-->
				    <div class="modal fade" id="id-sign-manager" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
				        aria-hidden="true">
				        <div class="modal-dialog" role="document">
				            <div class="modal-content">
				                <div class="modal-header">
				                    <h5 class="modal-title" id="exampleModalLabel">직인 목록</h5>
				                    <!-- <button class="close" type="button" data-dismiss="modal" aria-label="Close">
				                        <span aria-hidden="true">×</span>
				                    </button> -->
				                </div>
				                
				                <div class="modal-body">
				                	<div id="id_signList">
				                	<c:choose>
				                		<c:when test="${empty signList}">
				                			<div>직인이 없습니다. 직인을 추가해 주세요</div>
				                		</c:when>
				                		<c:otherwise>
					                		<c:forEach items="${signList}" var="i">
						                		<div class="signWrapper">
													<div class="sign-box" style="border: 1px dashed gray; width: 120px; height: 120px;">
														<img src="/signs/${i.saveName}" style="width: 100%; height: 100%;">
													</div>
													<div class="sign-name" style="border: 1px solid gray; font-weight: bold;">${i.originName}</div>
													
													<div class="sign-id" data-sign-id="${i.fileNum}"></div>
						                		</div>
					                		</c:forEach>				                		
				                		</c:otherwise>				                	
				                	</c:choose>
				                	</div>
				                </div>
				                
				                <div class="modal-footer">
				                
				                	
					                	<button class="btn btn-info" type="button" id="id_makeSign" 
					                	data-toggle="modal" data-target="#id-sign-maker">직인 생성</button>				                	
				                	
				                    <button class="btn btn-primary" type="button" id="id_useSign" >직인 사용</button>
				                    <button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
				                    
				                </div>
				            </div>
				        </div>
				    </div>
				    
				    
				    <!-- 직인 만들기 Modal-->
				    <div class="modal fade" id="id-sign-maker" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
				        aria-hidden="true">
				        <div class="modal-dialog" role="document">
				            <div class="modal-content">
				                <div class="modal-header">
				                    <h5 class="modal-title" id="exampleModalLabel">직인 생성</h5>
				                    <!-- <button class="close" type="button" data-dismiss="modal" aria-label="Close">
				                        <span aria-hidden="true">×</span>
				                    </button> -->
				                </div>
				                
				                <div class="modal-body"> <!-- 직인 컨텐츠 -->
				                	<div class="imageWrapper" id="id_imageWrapper" style="height:200px;">
				                		
				                	</div>
				                </div>
				                
				                <div class="modal-footer">				                
				                	<button class="btn btn-info" type="button" id="btn-bring-sign" >직인 가져오기</button>
				                    <button class="btn btn-info" type="button" id="btn-draw-sign" >직접 그리기</button>
				                    <button class="btn btn-primary" type="button" id="btn-send-sign" >추가하기</button>
				                    <button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
				                    
				                </div>
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
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
	
	<script src="/js/document/signTool.js"></script>
	
</body>
</html>