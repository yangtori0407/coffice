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
<script type="module"
	src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule
	src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">

					<!-- contents 내용 -->
					<div>

						<div class="card mb-4 py-3 border-left-info">
							<div class="card-body">${detail.boardTitle }</div>
						</div>

						<div class="mb-2">
							<button class="btn btn-danger" id="delBtn" type="button"
								data-board-num="${detail.boardNum }">삭제하기</button>
							<a href="./update?boardNum=${detail.boardNum }"
								class="btn btn-primary">수정하기</a>
						</div>
						<div class="card shadow mb-3" style="min-height: 600px;">
							<div class="card-header py-3">
								<h6 class="m-0 font-weight-bold text-primary">작성일:
									${detail.boardDate }</h6>
								<h6 class="m-0 font-weight-bold text-primary">조회수:
									${detail.boardHit }</h6>
							</div>
							<div class="card-body">${detail.boardContents }</div>
						</div>

					</div>
				</div>
				<!-- end Content -->
				<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
			</div>
			<!-- End Content Wrapper -->
		</div>
		<!-- End Wrapper -->
		<script>
		const delBtn = document.getElementById("delBtn");

		delBtn.addEventListener("click", ()=>{
		    console.log("click")
		    const boardNum = delBtn.getAttribute("data-board-num");
		    let param = new URLSearchParams();
		    param.append("boardNum", boardNum);

		    fetch("./delete", {
		        method: "POST",
		        body: param
		    })
		    .then(r => r.text())
		    .then(r =>{
		        if(r*1 == 1){
		            alert("삭제가 완료되었습니다.");
		            location.href = "/board/list";
		        }else{
		        	alert("삭제를 실패하였습니다.");
		        	location.href = "/board/list";
		        }

		        
		    })
		})
		</script>
		<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
</html>