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
form {
  width: 600px;
  margin: 200px auto;
  padding: 2rem;
  background: #fff;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
  font-family: 'Poppins', sans-serif;
}

/* 제목 */
.title {
	margin-bottom : 20px;
}

/* 입력 필드 묶음 */
.input-wrap {
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
}

/* 라벨 */
.input-wrap label {
  margin-bottom: 0.4rem;
  font-size: 0.95rem;
  color: #333;
}

/* input, select 필드 */
.input-wrap input[type="text"],
.input-wrap input[type="password"],
.input-wrap input[type="date"],
.input-wrap select {
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 20px;
  transition: border-color 0.3s;
}

/* 포커스 시 테두리 강조 */
.input-wrap input:focus,
.input-wrap select:focus {
  border-color: #007bff;
  outline: none;
}

/* 에러 메시지 박스 */
.error-box {
  width: 100%;
  margin-top: -5px;
}

/* 에러 텍스트 */
.error-message {
  color: red;
  font-size: 12px;
  margin-top: 4px;
  margin-left: 5px;
}

/* 제출 버튼 */
.btn {
  display: block;
  width: 100%;
  height: 50px;
  border-radius: 25px;
  border: none;
  background: linear-gradient(to right, #007BFF, #3399FF, #007BFF);
  background-size: 200%;
  font-size: 1.2rem;
  color: white;
  margin-top: 1.5rem;
  cursor: pointer;
  transition: background-position 0.3s;
}

.btn:hover {
  background-position: right;
}

.postcode-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
}

.postcode-box input[type="text"] {
  flex: 1;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 20px;
}

.postcode-box input[type="button"] {
  white-space: nowrap;
  padding: 10px 16px;
  background-color: #007bff;
  border: none;
  border-radius: 20px;
  color: #fff;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background-color 0.3s;
  width: 120px;
}

.postcode-box input[type="button"]:hover {
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
					<div class="card shadow mb-4" style="height:800px">
					<div class="form-container">
					
						<form:form modelAttribute="branchMasterVO" cssClass="branchMaster" action="./masterAdd" method="post">
						    <h3 class="title">Coffice 점주등록</h3>
						    <div class="input-wrap">
								<select class="form-select" id="selectUser" name="userId.userId" required>
									<option value="" selected>추가할 점주를 선택하세요</option>
									<c:forEach items="${notRegisterBranchMaster}" var="m">
									<option value="${m.userId.userId}">${m.userId.name},${m.userId.userId}</option>
									</c:forEach>						
								</select>
						    </div>
							<div class="input-wrap">
								<div class="postcode-box">
								    <form:input path="contactNumber" placeholder="사업자등록번호(-없이 10자리)" maxlength="10" required="true"/>
								    <input type="button" onclick="code_check();" value="확인">
								 </div>
							</div>
							<div class="error-box">
							    <form:errors path="contactNumber" cssClass="error-message"/>
							</div>
							<div id="serviceKeyHolder" data-servicekey="${servicekey}"></div>
							<div class="input-wrap">  
							    <form:input path="contactDate" type="date" cssClass="form-input" required="true" max="${today}"/>
							</div>
							<button type="submit" class="btn btn-primary">등록</button>
						</form:form>
						
					</div>
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