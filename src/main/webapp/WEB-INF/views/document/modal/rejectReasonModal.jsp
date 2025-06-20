<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <!-- 반려 처리 시 넘길 데이터 Form -->
	<form id="rejectForm" action="/document/reject" method="post">
		<input type="hidden" name="documentId" id="reject_documentId" value="${docuVO.documentId}">
		<input type="hidden" name="rejectReason" id="reject_reason">
		<input type="hidden" name="userId" id="reject_userId" value="${sessionScope.userVO.userId}">
	</form>
    
	<!-- 반려 사유 쓰기 Modal-->
    <div class="modal fade" id="writeRejectReason" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">반려 사유</h5>
                    <!-- <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button> -->
                </div>
                
                <div class="modal-body"> <!-- 반려 사유 내용 -->
                	<div class="rejectWrapper" id="id_rejectWrapper" style="height:300px;">
                		<div id="id_rejectContent" contenteditable="true" style="height:90%; width:90%; background-color: rgb(225, 255, 201);">
                		
                		</div>
                	</div>
                </div>
                
                <div class="modal-footer">                	
                	<button id="id_completeReject" class="btn btn-secondary" type="button">작성 완료</button>
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>                    
                </div>
            </div>
        </div>
    </div>
    
    
    <!-- 반려 사유 보기 Modal-->
    <div class="modal fade" id="rejectReasonModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">반려 사유</h5>
                    <!-- <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button> -->
                </div>
                
                <div class="modal-body"> <!-- 반려 사유 내용 -->
                	<div class="rejectWrapper" id="id_rejectWrapper2" style="height:300px;">
                		<div id="id_rejectContent2" contenteditable="true" style="height:90%; width:90%; background-color: rgb(225, 255, 201);">
                            <c:forEach items="${docuVO.approvalLineVOs}" var="i" varStatus="status">
                                <c:if test="${i.status eq '반려'}">
                                    ${i.rejectReason}
                                </c:if>
                            </c:forEach>
                		</div>
                	</div>
                </div>
                
                <div class="modal-footer">
                	
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
                    
                </div>
            </div>
        </div>
    </div>


