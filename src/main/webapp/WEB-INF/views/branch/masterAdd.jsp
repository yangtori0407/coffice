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
					<div style="width:400px; height:600px; margin: 0 auto;">
						<form action="./masterAdd" method="post">
							<select class="form-select" id="selectUser" name="userId.userId">
								<option selected>추가할 점주를 선택하세요</option>
								<c:forEach items="${notRegisterBranchMaster}" var="m">
								<option value="${m.userId.userId}">${m.userId.name},${m.userId.userId}</option>
								</c:forEach>						
							</select>
							<div class="input-box">
								<input type="text" name="contactNumber" id="contactNumber" placeholder="사업자등록번호" maxlength="10">
								<span style="cursor:hand" onclick="code_check();">확인</span>
							</div>
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
<script>
   function code_check(){
   	if(!checkInput_null('frm1','code1,code2,code3')){ frm1.overlap_code_ok.value = "";}
    else{
    	document.frm1.code.value = frm1.code1.value + frm1.code2.value + frm1.code3.value;
		var data = {
			"b_no":[document.frm1.code.value]	
		};
   			
   			$.ajax({
   		        url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=MnUQss9Aqei9MrPS7u5PuGDn6bHqVr3ej2KGG4CmUluPTK7gF5nbVBgnx9lNPB1E4HNe97uDig783%2BUgURa%2FQg%3D%3D", //활용 신청 시 발급 되는 serviceKey값을 [서비스키]에 입력해준다.
   		        type: "POST",
   		        data: JSON.stringify(data), // json 을 string으로 변환하여 전송
   		        dataType: "JSON",
   		        traditional: true,
   		        contentType: "application/json; charset:UTF-8",
   		        accept: "application/json",
   		        success: function(result) {
   		            console.log(result);
   		            if(result.match_cnt == "1") {
                    	//성공
   		                console.log("success");
   		             	document.frm1.submit();
   		            } else {
                    	//실패
   		                console.log("fail");
   		                alert(result.data[0]["tax_type"]);
   		            }
   		        },
   		        error: function(result) {
   		            console.log("error");
   		            console.log(result.responseText); //responseText의 에러메세지 확인
   		        }
   		    });
 		}
   }
</script>
</html>