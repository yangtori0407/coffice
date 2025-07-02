<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <title>COFFICE</title>
  <link href="/images/3.png" rel="shortcut icon" type="image/x-icon">
  <link href="https://fonts.googleapis.com/css?family=Poppins:600&display=swap" rel="stylesheet">
  <script src="https://kit.fontawesome.com/a81368914c.js"></script>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" href="/css/user/forgotPw.css">
  
  <c:if test="${not empty msg}">
	  <script>
	    alert("${msg}");
	  </script>
  </c:if>
  
  <c:if test="${not empty fail}">
	  <script>
	    alert("${fail}");
	  </script>
  </c:if>
  
  <c:if test="${not empty error}">
	  <script>
	    alert("${error}");
	  </script>
  </c:if>
  
  <c:if test="${not empty valid}">
	  <script>
	    alert("${valid}");
	  </script>
  </c:if>
  

  
</head>
<body>
  <img class="wave" src="/images/wave7.png">
  <div class="container">
    <div class="img">
      <!-- <img style="width:500px;" src="/images/login.png"> -->
    </div>
    <div class="login-content">
    
    
    
      <form action="/user/resetPw" method="post">
      	<img src="/images/login2.png">
        <h5 class="title">새로운 비밀번호를 입력해주세요.</h5>
              <div class="input-div one">
                 <div class="i">
                    <i class="fas fa-user"></i>
                 </div>
                 <div class="div">
                    <h5>password</h5>
                    <input type="password" class="input" name="password">
                 </div>
              </div>
              <div class="input-div pass">
                 <div class="i"> 
                    <i class="fas fa-lock"></i>
                 </div>
                 <div class="div">
                    <h5>password check</h5>
                    <input type="password" class="input" name="passwordCheck">
                 </div>
              </div>
              <input type="hidden" name="userId" value="${userId}">
              <input type="submit" class="btn" value="reset my pw">
            </form>
            
            
        </div>
    </div>
    
    <script src="/js/user/login.js" type="text/javascript"></script>
</body>
</html>