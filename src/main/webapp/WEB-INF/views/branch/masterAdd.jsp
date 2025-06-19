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
<style>
    .form-container {
        width: 400px;
        height: auto;
        margin: 50px auto;
        padding: 30px;
        background: #fff;
        border-radius: 12px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    }

    .form-container h2 {
        text-align: center;
        margin-bottom: 20px;
        color: #333;
    }

    .form-container select,
    .form-container input[type="text"],
    .form-container input[type="date"] {
        width: 100%;
        padding: 10px 15px;
        margin: 10px 0;
        border: 1px solid #ddd;
        border-radius: 8px;
        box-sizing: border-box;
        transition: border-color 0.3s;
    }

    .form-container select:focus,
    .form-container input[type="text"]:focus,
    .form-container input[type="date"]:focus {
        border-color: #007bff;
        outline: none;
    }

    .form-container span {
        display: inline-block;
        margin-top: 5px;
        padding: 8px 12px;
        background-color: #007bff;
        color: #fff;
        border-radius: 6px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .form-container span:hover {
        background-color: #0056b3;
    }

    .form-container button[type="submit"] {
        width: 100%;
        padding: 12px 0;
        background-color: #28a745;
        border: none;
        border-radius: 8px;
        color: white;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.3s;
        margin-top: 20px;
    }

    .form-container button[type="submit"]:hover {
        background-color: #218838;
    }
	
	.input-box {
	    display: flex;
	    align-items: center;
	}

	.input-box input[type="text"] {
	    flex: 1;
	    padding: 10px 15px;
	    margin: 10px 5px 10px 0; /* 오른쪽 간격 추가 */
	    border: 1px solid #ddd;
	    border-radius: 8px;
	    box-sizing: border-box;
	    transition: border-color 0.3s;
	}

	.input-box span {
	    padding: 10px 15px;
	    background-color: #007bff;
	    color: #fff;
	    border-radius: 8px;
	    cursor: pointer;
	    transition: background-color 0.3s;
	    white-space: nowrap; /* 줄바꿈 방지 */
	}

	.input-box span:hover {
	    background-color: #0056b3;
	}
</style>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">
					<!-- contents 내용 -->
					<div class="form-container">
					    <h2>점주 등록</h2>
						<form action="./masterAdd" method="post">
							<select class="form-select" id="selectUser" name="userId.userId">
								<option selected>추가할 점주를 선택하세요</option>
								<c:forEach items="${notRegisterBranchMaster}" var="m">
								<option value="${m.userId.userId}">${m.userId.name},${m.userId.userId}</option>
								</c:forEach>						
							</select>
							<div class="input-box">
								<input type="text" id="contactNumber" placeholder="사업자등록번호(10자리)" maxlength="10">
								<span style="cursor:pointer" onclick="code_check();">확인</span>
							</div>
							
							<input type="hidden" name="contactNumber" id="hiddenContactNumber" value="">
							<div id="serviceKeyHolder" data-servicekey="${servicekey}"></div>
							<div class="input-box">	
								<input type="date" name="contactDate" id="contactDate" placeholder="사업자등록날짜">					
							</div>
							<button type="submit" class="btn btn-primary">점주등록</button>
						</form>
					</div>
				</div>
			</div>
			<!-- end Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
	</div>
	<!-- End Wrapper -->
	<c:import url="/WEB-INF/views/templates/footModal.jsp"></c:import>
</body>
<script src="/js/branch/masterAdd.js">></script>
</html>