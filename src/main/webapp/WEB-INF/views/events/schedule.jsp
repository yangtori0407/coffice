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
			<!-- add Modal -->
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

								<c:if test="${kind eq '일정' }">
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
								</c:if>
								<c:if test="${kind eq '휴가' }">
									<div class="form-group">
										<label for="vType">종류</label>
										<div class="input-group">
											<select name="vType" id="vType" class="form-control">
												<option value="연차">연차</option>
												<option value="병가">병가</option>
												<option value="경조사">경조사</option>
												<option value="보상">보상 휴가</option>
											</select>
										</div>
									</div>
								</c:if>

								<hr>

								<div class="form-group">
									<label for="sDate">시작일</label>
									<div class="input-group">
										<input type="date" class="form-control" id="sDate">
										<select name="sTime" id="sTime" class="form-control">
											<option value="" selected>선택</option>
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
											<option value="" selected>선택</option>
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
											<input class="form-check-input" type="radio" name="radioOptions" id="radio1" value="weekly" checked>
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
											<label for="repeat">반복 종료</label>
											<div class="input-group" id="repeat">
												<input type="date" class="form-control" id="eRepeat">
											</div>
										</div>
										<div class="form-group">
											<label for="repeat">반복 횟수</label>
											<div class="input-group" id="repeat">
												<input type="number" class="form-control" id="repeatCount">
											</div>
										</div>
									</div>
								</c:if>
								<c:if test="${kind eq '휴가' }"></c:if>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary" id="send">Save</button>
						</div>
					</div>
				</div>
			</div>

			<!-- detail Modal -->
			<div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="detailModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content" id="detailModalContent">
						<div class="modal-header">
							<h5 class="modal-title" id="detailModalLabel">${kind}</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<form action="#">

									<div class="form-check form-check-inline">
										<input class="form-check-input" type="radio" name="detailResultOptions" id="inlineRadio1" value="private" disabled>
										<label class="form-check-label" for="inlineRadio1">Private</label>
									</div>
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="radio" name="detailResultOptions" id="inlineRadio2" value="public" disabled>
										<label class="form-check-label" for="inlineRadio2">Public</label>
									</div>
									<div class="form-check form-check-inline">
										<input class="form-check-input" type="radio" name="detailResultOptions" id="inlineRadio3" value="group" disabled>
										<label class="form-check-label" for="inlineRadio3">Group</label>
									</div>
	
									<hr>
									
									<div class="form-group">
										<label for="details">Details</label>
										<textarea class="form-control" id="detailResult" rows="3" disabled></textarea>
									</div>

								<hr>

								<div class="form-group">
									<label for="sDate">시작일</label>
									<div class="input-group">
										<input type="date" class="form-control" id="rsDate" disabled>
										<select name="rsTime" id="rsTime" class="form-control" disabled>
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
										<input type="date" class="form-control" id="reDate" disabled>
										<select name="reTime" id="reTime" class="form-control" disabled>
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
								<hr>

									<div class="form-check">
										<div class="">
											<input class="form-check-input" type="checkbox" id="repeatCheck">
											<label class="form-check-label" for="repeatCheck">
												반복 일정 일괄 변경
											</label>
										</div>
									</div>

							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" id="change">change</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal" id="saveChange">Save Changes</button>
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
	

	<script src='https://cdn.jsdelivr.net/npm/rrule@2.6.4/dist/es5/rrule.min.js'></script>
	<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.17/index.global.min.js'></script>
	<script src='https://cdn.jsdelivr.net/npm/@fullcalendar/rrule@6.1.17/index.global.min.js'></script>

	<c:if test="${kind eq '휴가' }">
		<script type="text/javascript" src="/js/calendar/vacationCalendar.js"></script>
	</c:if>
	<c:if test="${kind eq '일정' }">
		<script type="text/javascript" src="/js/calendar/scheduleCalendar.js"></script>
	</c:if>
</body>
</html>