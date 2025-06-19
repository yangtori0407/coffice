<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    

    <!-- 직인 만들기 Modal-->
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
                
                <div class="modal-body"> <!-- 직인 컨텐츠 -->
                	<div class="imageWrapper" id="id_imageWrapper" style="height:200px;">
                		
                	</div>
                </div>
                
                <div class="modal-footer">			                
                	
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
                    
                </div>
            </div>
        </div>
    </div>


