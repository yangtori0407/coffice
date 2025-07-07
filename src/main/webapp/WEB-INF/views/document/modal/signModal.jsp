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
                    <h4 class="modal-title" id="exampleModalLabel" style="font-weight: bolder;">직인 목록</h4>
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
                
                	<button class="btn btn-success" type="button" id="id_makeSign" data-toggle="modal" data-target="#id-sign-maker" >직인 생성</button>
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
                    <h4 class="modal-title" id="exampleModalLabel" style="font-weight: bolder;">직인 생성</h4>
                    <!-- <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button> -->
                </div>
                
                <div class="modal-body"> <!-- 직인 컨텐츠 -->
  
  
					  <div id="id_imageWrapper" style="display: flex; height: 200px; gap: 10px; padding: 5px;">
					  
					  <!-- 왼쪽: Canvas -->
					  <div style="flex: 1; display: flex; flex-direction: column; justify-content: center; align-items: center;">
					    <canvas id="signCanvas" width="200" height="180" 
					            style="border:1px solid #ccc; background:white; cursor:crosshair;">
					    </canvas>
					  </div>
					
					  <!-- 오른쪽: 선 굵기 + 색상 + 이름 입력 -->
					  <div style="flex: 1; display: flex; flex-direction: column; justify-content: space-between;">
					    
					    <!-- 위쪽: 선 굵기, 색상 -->
					    <div>
					      <div style="margin-bottom: 8px;">
					        <label style="font-size: 18px;">
					          선 굵기:
					          <select id="lineWidthSelect" class="form-select form-select-sm" style="width: 80px; display: inline-block;">
					            <option value="2">얇게</option>
					            <option value="4" selected>보통</option>
					            <option value="6">두껍게</option>
					          </select>
					        </label>
					      </div>
					
					      <div>
					        <label style="font-size: 18px;">
					          색 &nbsp; &nbsp; 상:
					          <input type="color" id="lineColorPicker" value="#000000" style="vertical-align: middle;">
					        </label>
					      </div>
					    </div>
					
					    <!-- 아래쪽: 이름 입력 -->
					    <div>
					      <input type="text" id="signNameInput" placeholder="직인명 또는 용도를 적어주세요"
					             style="width: 100%; font-size: 15px; padding: 6px; margin-bottom: 5px;" >
					    </div>
					    
					  </div>
					
					</div>
  
  
  
				</div>

                
                
                <div class="modal-footer">
                    <button id="clearCanvasBtn" class="btn btn-warning">초기화</button>
                    <button class="btn btn-primary" type="button" id="btn-send-sign" >추가하기</button>
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
                    
                </div>
            </div>
        </div>
    </div>
    
    
    
    
    
    
    
    

