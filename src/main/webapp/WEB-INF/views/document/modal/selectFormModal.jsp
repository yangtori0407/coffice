<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    
	<!-- 직인 메뉴 모달 -->
    <div class="modal fade" id="formMenu" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header d-flex align-items-center justify-content-center">
                    <div class="modal-title text-info" id="exampleModalLabel" style="font-size: x-large; font-weight: bold;">문서 양식 목록</div>
                    <!-- <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button> -->
                </div>
                
                <div class="modal-body">
                	<div class="" id="id_formList" style="overflow-y: auto; height: 170px; text-align: center;">
                		
                	</div>
                </div>
                
                <div class="modal-footer">
                
                   <button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
                    
                </div>
            </div>
        </div>
    </div>
    
    <style>
    	 .formWrappers:hover {
		  	cursor: pointer;
			background-color: #f8f9fa;
		  }	
		  
		 .formWrappers:active {
		  	
			background-color: rgb(188, 218, 230);
		  }	
    </style>
    
    
    
    
    

