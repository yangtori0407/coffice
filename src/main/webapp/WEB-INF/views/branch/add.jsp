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
        margin: 50px auto;
        padding: 30px;
        background: #fff;
        border-radius: 12px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }

    .form-container h2 {
        text-align: center;
        margin-bottom: 20px;
        color: #333;
        font-weight: bold;
    }

    .input-box {
        width: 100%;
        margin: 15px 0;
    }

    .input-box input[type="text"] {
        width: 100%;
        padding: 10px 15px;
        border: 1px solid #ddd;
        border-radius: 8px;
        box-sizing: border-box;
        transition: border-color 0.3s;
        font-size: 14px;
    }

    .input-box input[type="text"]:focus {
        border-color: #007bff;
        outline: none;
    }

    .postcode-box {
        display: flex;
        gap: 10px;
        align-items: center;
    }

    .postcode-box input[type="text"] {
        flex: 1;
    }

    .postcode-box input[type="button"] {
        padding: 8px 12px;
        background-color: #007bff;
        border: none;
        border-radius: 6px;
        color: #fff;
        font-size: 13px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .postcode-box input[type="button"]:hover {
        background-color: #0056b3;
    }

    button[type="submit"] {
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

    button[type="submit"]:hover {
        background-color: #218838;
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
					<div class="form-container">
					    <h2>지점 등록</h2>
						<form:form modelAttribute="branchVO" action="./add" method="post">
							  <div class="input-box">
							    <form:input type="text" path="branchName" placeholder="지점 이름" required="true"/>  
							  </div>
							  <div class="error-box">
							    <form:errors path="branchName" cssClass="error-message"/>
							  </div>
							       <div class="input-box postcode-box">
							           <form:input type="text" path="branchPostcode" placeholder="지점 우편번호" required="true" readonly="true"/>
							           <input type="button" onclick="daumPostcode()" value="우편번호 찾기">
							       </div>
							       <div class="input-box">
							           <form:input type="text" path="branchAddress" placeholder="지점 주소" readonly="true"/>
							       </div>
							<button type="submit" class="btn btn-primary">지점등록</button>
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
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/js/branch/add.js"></script>
</html>