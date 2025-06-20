<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
	.form-container .form-input {
	    width: 100%;
	    padding: 10px 15px;
	    margin: 10px 0;
	    border: 1px solid #ddd;
	    border-radius: 8px;
	    box-sizing: border-box;
	    transition: border-color 0.3s;
	}

	.form-container select:focus,
	.form-container .form-input:focus {
	    border-color: #007bff;
	    outline: none;
	}

	.input-box span {   /* 확인 버튼에만 적용됨 */
	    display: inline-block;
	    margin-top: 5px;
	    padding: 8px 12px;
	    background-color: #007bff;
	    color: #fff;
	    border-radius: 6px;
	    cursor: pointer;
	    transition: background-color 0.3s;
	    white-space: nowrap;
	}

	.input-box span:hover {
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

	.input-box .form-input {
	    flex: 1;
	    padding: 10px 15px;
	    margin: 10px 5px 10px 0;
	    border: 1px solid #ddd;
	    border-radius: 8px;
	    box-sizing: border-box;
	    transition: border-color 0.3s;
	}

	.error-box {
	    width: 100%;
	    margin-top: -5px;
	}

	.error-message {
	    color: red;
	    font-size: 12px;
	    margin-left: 5px;
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
						<form:form modelAttribute="branchMasterVO" cssClass="branchMaster" action="./masterAdd" method="post">
							<select class="form-select" id="selectUser" name="userId.userId">
								<option selected>추가할 점주를 선택하세요</option>
								<c:forEach items="${notRegisterBranchMaster}" var="m">
								<option value="${m.userId.userId}">${m.userId.name},${m.userId.userId}</option>
								</c:forEach>						
							</select>
							<div class="input-box">
							    <form:input path="contactNumber" cssClass="form-input" placeholder="사업자등록번호(10자리)" maxlength="10" required="true"/>
							    <span style="cursor:pointer" onclick="code_check();">확인</span>
							</div>
							<div class="error-box">
							    <form:errors path="contactNumber" cssClass="error-message"/>
							</div>
							<div id="serviceKeyHolder" data-servicekey="${servicekey}"></div>
							<div class="input-box">  
							    <form:input path="contactDate" type="date" cssClass="form-input" required="true" max="${today}"/>
							</div>
							<button type="submit" class="btn btn-primary">점주등록</button>
						</form:form>
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
<script src="/js/branch/masterAdd.js"></script>
</html>