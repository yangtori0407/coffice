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
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">

					<!-- contents 내용 -->
					
					<h1 class="h3 mb-2 text-gray-800" id="kind">${kind} </h1>
					<div id="calendar"></div>

				</div>
			</div>
			<!-- end Content -->
			<!-- Modal -->
			<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">
								<c:if test="${kind eq '일정' }">${kind} 추가</c:if>
								<c:if test="${kind eq '휴가' }">${kind} 신청</c:if>
							</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<form action="#">

								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="private" checked>
									<label class="form-check-label" for="inlineRadio1">Private</label>
								</div>
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="public">
									<label class="form-check-label" for="inlineRadio2">Public</label>
								</div>
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3" value="group">
									<label class="form-check-label" for="inlineRadio3">Group</label>
								</div>

								<hr>

								<div class="form-group">
									<label for="details">Details</label>
									<textarea class="form-control" id="details" rows="3"></textarea>
								</div>

								<hr>

								<div class="form-group">
									<label for="sDate">시작일</label>
									<div class="input-group">
										<input type="date" class="form-control" id="sDate">
										<select name="sTime" id="sTime" class="form-control">
											<option value="09:00">09:00</option>
											<option value="10:00">10:00</option>
											<option value="11:00">11:00</option>
											<option value="12:00">12:00</option>
											<option value="13:00">13:00</option>
											<option value="14:00">14:00</option>
											<option value="15:00">15:00</option>
											<option value="16:00">16:00</option>
											<option value="17:00">17:00</option>
											<option value="18:00">18:00</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label for="eDate">종료일</label>
									<div class="input-group">
										<input type="date" class="form-control" id="eDate">
										<select name="eTime" id="eTime" class="form-control">
											<option value="09:00">09:00</option>
											<option value="10:00">10:00</option>
											<option value="11:00">11:00</option>
											<option value="12:00">12:00</option>
											<option value="13:00">13:00</option>
											<option value="14:00">14:00</option>
											<option value="15:00">15:00</option>
											<option value="16:00">16:00</option>
											<option value="17:00">17:00</option>
											<option value="18:00" selected>18:00</option>
										</select>
									</div>
								</div>
								<hr>
								<c:if test="${kind eq '일정' }">
									<div class="form-check">
										<div class="">
											<input class="form-check-input" type="checkbox" id="gridCheck">
											<label class="form-check-label" for="gridCheck">
												반복 설정
											</label>
										</div>
									</div>
									<div class="form-group" id="repeat-condition" style="display: none;">
										<div class="form-check form-check-inline">
											<input class="form-check-input" type="radio" name="radioOptions" id="radio1" value="weekly">
											<label class="form-check-label" for="radio1">Weekly</label>
										</div>
										<div class="form-check form-check-inline">
											<input class="form-check-input" type="radio" name="radioOptions" id="radio2" value="monthly">
											<label class="form-check-label" for="radio2">Monthly</label>
										</div>
										<div class="form-check form-check-inline">
											<input class="form-check-input" type="radio" name="radioOptions" id="radio3" value="yearly">
											<label class="form-check-label" for="radio3">Yearly</label>
										</div>
										<hr>
										<div class="form-group">
											<label for="repeat">반복 시작 - 종료</label>
											<div class="input-group" id="repeat">
												<input type="date" class="form-control" id="sRepeat">
												<input type="date" class="form-control" id="eRepeat">
											</div>
										</div>
									</div>
								</c:if>
								<c:if test="${kind eq '휴가' }"></c:if>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary" id="send">Save changes</button>
						</div>
					</div>
				</div>
			</div>

			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
	</div>
	<!-- End Wrapper -->
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
	<script src='/fullcalendar/dist/index.global.js'></script>
	<script type="text/javascript" src="/js/calendar/fullCalendar.js"></script>
</body>
</html>