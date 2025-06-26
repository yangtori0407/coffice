<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<nav
	class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

	<!-- Sidebar Toggle (Topbar) -->
	<!-- <button id="sidebarToggleTop"
		class="btn btn-link d-md-none rounded-circle mr-3">
		<i class="fa fa-bars"></i>
	</button> -->
	<div class="mt-2 ml-3">
		<h4>${kind }</h4>
	</div>


	<!-- Topbar Navbar -->
	<ul class="navbar-nav ml-auto">

		<!-- Nav Item - Search Dropdown (Visible Only XS) -->
		<li class="nav-item dropdown no-arrow d-sm-none"><a
			class="nav-link dropdown-toggle" href="#" id="searchDropdown"
			role="button" data-toggle="dropdown" aria-haspopup="true"
			aria-expanded="false"> <i class="fas fa-search fa-fw"></i>
		</a> <!-- Dropdown - Messages -->
			<div
				class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
				aria-labelledby="searchDropdown">
				<form class="form-inline mr-auto w-100 navbar-search">
					<div class="input-group">
						<input type="text" class="form-control bg-light border-0 small"
							placeholder="Search for..." aria-label="Search"
							aria-describedby="basic-addon2">
						<div class="input-group-append">
							<button class="btn btn-primary" type="button">
								<i class="fas fa-search fa-sm"></i>
							</button>
						</div>
					</div>
				</form>
			</div></li>

		<!-- Nav Item - Alerts -->
		<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal" var="user" />
			<input type="hidden" id="userId" value="${user.userId}">

			<li class="nav-item dropdown no-arrow mx-1 position-relative" id="alert"><a
				class="nav-link dropdown-toggle" href="#" id="alertsDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> <ion-icon name="notifications-outline"></ion-icon>
					<!-- Counter - Alerts --> <span
					class="badge badge-danger badge-counter" id="totalArea"></span>
			</a> <!-- Dropdown - Alerts -->
				<div id="alarmTooltip" class="alarm-tooltip">🔔 알림!</div>
				<div
					class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
					aria-labelledby="alertsDropdown">
					<h6 class="dropdown-header"
						style="font-size: 15px; font-weight: initial;">알림</h6>
					<div id="notificationArea"></div>
					<!-- 	<a class="dropdown-item d-flex align-items-center" id="" href="#">
					<div class="mr-3">
						<div class="icon-circle bg-info">
							<ion-icon size="large" name="information-circle-outline"></ion-icon>
						</div>
					</div>
					<div>
						<div class="small text-gray-500">December 12, 2019</div>
						<span class="font-weight-bold">A new monthly report is
							ready to download!</span>
					</div>
				</a>-->

					<button type="button" id="moreNotiBtn"
						class="btn btn-primary dropdown-item text-center small text-gray-500"
						data-toggle="modal" data-target="#moreNotification">알림
						더보기</button>
				</div></li>
		</sec:authorize>

		<!-- Modal -->
		<div class="modal fade" id="moreNotification" data-backdrop="static"
			data-keyboard="false" tabindex="-1"
			aria-labelledby="staticBackdropLabel" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="staticBackdropLabel">알림</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body p-0" id="notificationModal">
						<!-- <a class="d-flex align-items-center" id="" href="#">
							<div class="mr-3">
								<div class="icon-circle bg-info">
									<ion-icon size="large" name="information-circle-outline"></ion-icon>
								</div>
							</div>
							<div>
								<div class="small text-gray-500">December 12, 2019</div>
								<span class="font-weight-bold">A new monthly report is
									ready to download!</span>
							</div>
						</a> -->
					</div>
					<button type="button" id="moreNotiModalBtn"
						class="dropdown-item text-center small text-gray-500">알림
						더보기</button>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">닫기</button>
					</div>
				</div>
			</div>
		</div>

		<!-- Nav Item - Messages -->
		<li class="nav-item"><a class="nav-link no-arrow mx-1"
			href="/user/mypage" id="messagesDropdown" role="button"
			aria-expanded="false"> <ion-icon name="person-outline"></ion-icon>
				<!-- Counter - Messages -->
		</a></li>
		<li class="nav-item"><a class="nav-link no-arrow mx-1"
			onclick="confirmLogout()" id="messagesDropdown" role="button"
			aria-expanded="false"> <ion-icon name="log-out-outline"></ion-icon>
		</a></li>


		<div class="topbar-divider d-none d-sm-block"></div>

		<!-- Nav Item - User Information -->

		<li class="nav-item no-arrow"><span class="nav-link"
			id="userDropdown" aria-haspopup="true" aria-expanded="false">
				<span class="mr-2 d-none d-lg-inline text-gray-600 small"> <c:if
						test="${not empty user}">
				    ${user.name} ${user.position}
				</c:if></span> <img class="img-profile rounded-circle" src="/images/coffice.png">
		</span> <!-- Dropdown - User Information --></li>


	</ul>

</nav>
<form id="logoutForm" action="/user/logout" method="post"
	style="display: none;">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
</form>

<script src="/js/user/logout.js"></script>