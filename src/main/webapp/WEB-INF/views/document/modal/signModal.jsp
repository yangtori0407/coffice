<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    
	<!-- 결재 및 직인사용 시 넘길 데이터 Form -->
	<form id="proceedForm" action="/document/proceed" method="post">
		<input type="hidden" name="documentId" id="documentId" value="${docuVO.documentId}">
		<input type="hidden" name="signId" id="signId">
		<input type="hidden" name="userId" id="userId" value="${sessionScope.user.userId}">
	</form>


	<!-- 직인 메뉴 모달 -->
    <div class="modal fade" id="signMenu" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
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
                
                	<button class="btn btn-primary" type="button" id="id_makeSign" data-toggle="modal" data-target="#id-sign-maker" >직인 생성</button>
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
                		<div>
                            <label>선 굵기: 
                                <select id="lineWidthSelect" class="form-select form-select-sm" style="width: 80px; display:inline-block;">
                                    <option value="2">얇게</option>
                                    <option value="4" selected>보통</option>
                                    <option value="6">두껍게</option>
                                </select>
                            </label>

                            <label style="margin-left: 10px;">색상: 
                                <input type="color" id="lineColorPicker" value="#000000" style="vertical-align: middle;">
                            </label>
                        </div>

                        <canvas id="signCanvas" width="160" height="160" 
                            style="border:1px solid #ccc; background:white; cursor:crosshair;">
                        </canvas>

                        <div>
                            <label style="margin-left: 10px;"> 
                                <input type="text" id="signNameInput" placeholder="직인명 또는 용도를 적어주세요">
                            </label>
                        </div>

                        <div style="margin-top: 10px;">
                            <button id="clearCanvasBtn" class="btn btn-sm btn-warning">초기화</button>
                        </div>
                	</div>
                </div>
                
                <div class="modal-footer">                    
                    <button class="btn btn-primary" type="button" id="btn-send-sign" >추가하기</button>
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
                    
                </div>
            </div>
        </div>
    </div>
    
    
    
    
    
    
    
    

