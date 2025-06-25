<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul
	class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
	id="accordionSidebar">

	<!-- Sidebar - Brand -->
	<a
		class="sidebar-brand d-flex align-items-center justify-content-center"
		href="/">
		<div>
			<img style="width: 30px;" src="/images/coffice.png">
		</div>

		<div class="sidebar-brand-text">
			<img style="width: 120px;" src="/images/large_coffice.png">
		</div>

	</a>

	<!-- Divider -->
	<hr class="sidebar-divider my-0">

	<!-- Nav Item - Dashboard -->
	<li class="nav-item active"><a class="nav-link" href="/"> <ion-icon
				name="home-outline"></ion-icon> <span> 홈</span>
	</a></li>

	<!-- Divider -->
	<hr class="sidebar-divider">

	<!-- Heading
	<div class="sidebar-heading">Interface</div> -->

	<!-- Nav Item - Pages Collapse Menu -->
	<li class="nav-item"><a class="nav-link collapsed" href="#"
		data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true"
		aria-controls="collapseTwo"> <ion-icon
				name="document-attach-outline"></ion-icon> <span>결재</span>
	</a>
		<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
			data-parent="#accordionSidebar">
			<div class="bg-white py-2 collapse-inner rounded">
				<a class="collapse-item" href="/document/selectform">기안 작성</a> <a
					class="collapse-item" href="/document/list/online">기안 문서함</a> <a
					class="collapse-item" href="/document/list/onwaiting">결재 대기 문서함</a>
				<a class="collapse-item" href="/document/list/onreference">참조
					문서함</a> <a class="collapse-item" href="/document/list/handled">승인/반려
					문서함</a> <a class="collapse-item" href="/document/list/ontemporary">임시
					저장 문서함</a>
			</div>
		</div></li>

	<!-- Nav Item - Utilities Collapse Menu -->
	<li class="nav-item"><a class="nav-link collapsed" href="#"
		data-toggle="collapse" data-target="#collapseUtilities"
		aria-expanded="true" aria-controls="collapseUtilities"><ion-icon
				name="list-outline"></ion-icon><span> 게시판</span> </a>
		<div id="collapseUtilities"
			class="collapse ${posts == ('notice' or 'board') ? 'show' :''}"
			aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
			<div class="bg-white py-2 collapse-inner rounded">
				<a class="collapse-item ${posts == 'notice' ? 'active' : ''}"
					href="/notice/list">공지사항</a> <a
					class="collapse-item ${posts == 'board' ? 'active' : '' }"
					href="/board/list">익명게시판</a>
			</div>
		</div></li>

	<!-- Nav Item - Pages Collapse Menu -->
	<!-- Nav Item - Tables -->
	<li class="nav-item"><a class="nav-link collapsed" href="#"
		data-toggle="collapse" data-target="#collapseCalendar"
		aria-expanded="true" aria-controls="collapseCalendar"> <ion-icon
				name="calendar-outline"></ion-icon> <span>일정</span>
	</a>
		<div id="collapseCalendar"
			class="collapse ${events == ('schedule' or 'vacation') ? 'show' :''}"
			aria-labelledby="headingCalendar" data-parent="#accordionSidebar">
			<div class="bg-white py-2 collapse-inner rounded">
				<a class="collapse-item ${events == 'schedule' ? 'active' : ''}"
					href="/events/schedule">일정 관리</a> <a
					class="collapse-item ${events == 'vacation' ? 'active' : ''}"
					href="/events/vacation">휴가 관리</a>
			</div>

		</div></li>

		
		
		<li class="nav-item">
			<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseBranch" aria-expanded="true" aria-controls="collapseCalendar">
			<ion-icon name="map-outline"></ion-icon>
				<span>지점</span>
			</a>
			<div id="collapseBranch" class="collapse  ${branch == ('map' or 'add' or 'masterAdd' or 'myBranch') ? 'show' :''}" aria-labelledby="headingCalendar"
				data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<a class="collapse-item ${branch == ('map') ? 'active' :''}" href="/branch/map">지점지도</a>
					<a class="collapse-item ${branch == ('add') ? 'active' :''}" href="/branch/add">지점등록</a>
					<a class="collapse-item ${branch == ('masterAdd') ? 'active' :''}" href="/branch/masterAdd">점주등록</a>
					<a class="collapse-item ${branch == ('myBranch') ? 'active' :''}" href="/branch/myBranch">my지점</a>
				</div>



	<li class="nav-item"><a class="nav-link collapsed" href="#"
		data-toggle="collapse" data-target="#collapseBranch"
		aria-expanded="true" aria-controls="collapseCalendar"> <ion-icon
				name="map-outline"></ion-icon> <span>지점</span>
	</a>
		<div id="collapseBranch" class="collapse"
			aria-labelledby="headingCalendar" data-parent="#accordionSidebar">
			<div class="bg-white py-2 collapse-inner rounded">
				<a class="collapse-item" href="/branch/map">지점지도</a> <a
					class="collapse-item" href="/branch/add">지점추가</a> <a
					class="collapse-item" href="/branch/masterAdd">점주등록</a> <a
					class="collapse-item" href="/branch/myBranch">my지점</a>
			</div>

		</div></li>

	<li class="nav-item"><a class="nav-link ${ingredients == 'ingredients' ? 'ingredients-active' :''}" href="/ingredients/list">
		<ion-icon name="cube-outline"></ion-icon> <span>물류</span>
	</a></li>

	<!-- Nav Item - Charts -->
	<li class="nav-item"><a id="chatSideBar"
		class="nav-link ${chat == 'chat' ? 'chat-active' : '' }"
		href="/chat/main"> <ion-icon name="chatbubbles-outline"></ion-icon>
			<span>메신저</span>
	</a></li>

	<%-- <!-- Nav Item - Tables -->
	<li class="nav-item"><a
		class="nav-link ${message == 'message' ? 'chat-active' : '' }"
		href="/message/main"> <ion-icon name="mail-outline"></ion-icon> <span>사내
				이메일</span></a></li> --%>

	<li class="nav-item"><a class="nav-link collapsed" href="#"
		data-toggle="collapse" data-target="#collapse"
		aria-expanded="true" aria-controls="collapseUtilities"><ion-icon name="mail-outline"></ion-icon><span> 이메일</span> </a>
		<div id="collapse"
			class="collapse ${message == ('sender' or 'receiver') ? 'show' :''}"
			aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
			<div class="bg-white py-2 collapse-inner rounded">
				<a class="collapse-item ${message == 'receive' ? 'active' : '' }"
					href="/message/receive">받은 이메일</a> <a
					class="collapse-item ${message == 'send' ? 'active' : ''}"
					href="/message/send">보낸 이메일</a>
			</div>
		</div></li>

	<!-- Divider -->
	<hr class="sidebar-divider d-none d-md-block">

	<!-- Sidebar Toggler (Sidebar) -->
	<div class="text-center d-none d-md-inline">
		<button class="rounded-circle border-0" id="sidebarToggle"></button>
	</div>

	<!-- <div class="sidebar-card d-none d-lg-flex">
			날씨?
		</div> -->



</ul>


