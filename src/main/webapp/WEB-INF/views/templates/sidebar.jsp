<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

		<!-- Sidebar - Brand -->
		<a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
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
		<li class="nav-item active"><a class="nav-link" href="/">
				<ion-icon name="home-outline"></ion-icon> <span> 홈</span>
			</a></li>

		<!-- Divider -->
		<hr class="sidebar-divider">

		<!-- Heading
	<div class="sidebar-heading">Interface</div> -->

		<!-- Nav Item - Pages Collapse Menu -->
		<li class="nav-item"><a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
				aria-expanded="true" aria-controls="collapseTwo"> <ion-icon name="document-attach-outline"></ion-icon>
				<span>결재</span>
			</a>
			<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<a class="collapse-item" href="">기안 작성</a>
					<a class="collapse-item" href="">기안 문서함</a>
					<a class="collapse-item" href="">결재 대기 문서함</a>
					<a class="collapse-item" href="">참조 문서함</a>
					<a class="collapse-item" href="">임시 저장 문서함</a>
				</div>
			</div>
		</li>

		<!-- Nav Item - Utilities Collapse Menu -->
		<li class="nav-item"><a class="nav-link collapsed" href="#" data-toggle="collapse"
				data-target="#collapseUtilities" aria-expanded="true" aria-controls="collapseUtilities"><ion-icon
					name="list-outline"></ion-icon><span> 게시판</span>
			</a>
			<div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities"
				data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<a class="collapse-item" href="/notice/list">공지사항</a> 
					<a class="collapse-item" href="/board/list">익명게시판</a>
				</div>
			</div>
		</li>

		<!-- Nav Item - Pages Collapse Menu -->
		<!-- Nav Item - Tables -->
		<li class="nav-item"><a class="nav-link" href="#"> <ion-icon name="calendar-outline"></ion-icon>
				<span>일정</span></a></li>

		<!-- Nav Item - Charts -->
		<li class="nav-item"><a class="nav-link" href="charts.html"> <ion-icon name="chatbubbles-outline"></ion-icon>
				<span>메신저</span></a></li>

		<!-- Nav Item - Tables -->
		<li class="nav-item"><a class="nav-link" href="tables.html"> <ion-icon name="mail-outline"></ion-icon>
				<span>사내 이메일</span></a></li>

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