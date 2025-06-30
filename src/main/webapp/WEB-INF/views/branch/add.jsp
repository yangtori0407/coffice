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

/* 타이틀 */
.title {
	margin-bottom : 20px;
}

/* 입력 감싸는 레이아웃 */
.input-wrap {
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
}

/* label 스타일 */
.input-wrap label {
  margin-bottom: 0.4rem;
  font-size: 0.95rem;
  color: #333;
}

/* 텍스트 입력 필드 스타일 */
.input-wrap input[type="text"],
.input-wrap input[type="password"],
.input-wrap select {
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 20px;
  transition: border-color 0.3s;
}

/* 포커스 시 테두리 컬러 강조 */
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

/* 에러 메시지 텍스트 */
.error-message {
  color: red;
  font-size: 12px;
  margin-top: 4px;
  margin-left: 5px;
}

/* 우편번호 입력 줄 정렬 */
.postcode-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
}

/* 우편번호 입력창 */
.postcode-box input[type="text"] {
  flex: 1;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 20px;
}

/* 우편번호 찾기 버튼 */
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
}

.postcode-box input[type="button"]:hover {
  background-color: #0056b3;
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
</style>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/top.jsp"></c:import>
				<div class="container-fluid">
				<div class="card shadow mb-4" style="height:800px">
					<div class="form-container">
						<form:form modelAttribute="branchVO" action="./add" method="post">
					    <h3 class="title">Coffice 지점등록</h3>
							  <div class="input-wrap">
							    <form:input type="text" path="branchName" placeholder="지점 이름" required="true"/>  
								  <div class="error-box">
								    <form:errors path="branchName" cssClass="error-message"/>
								  </div>
							  </div>
							       <div class="input-wrap">
								       <div class="postcode-box">
								           <form:input type="text" path="branchPostcode" placeholder="지점 우편번호" required="true" onfocus="this.blur()"/>
								           <input type="button" onclick="daumPostcode()" value="우편번호 찾기">
								       </div>
							       </div>
							       <div class="input-wrap">
							           <form:input type="text" path="branchAddress" placeholder="지점 주소" readonly="true"/>
								       <div class="error-box">
									    <form:errors path="branchAddress" cssClass="error-message"/>
									  </div>
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
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/js/branch/add.js"></script>
</html>