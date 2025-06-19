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
  
  <c:if test="${not empty error}">
  <script>
    alert("${error}");
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
    
    
    
      <form action="/user/forgotPw" method="post">
      	<img src="/images/login2.png">
        <h5 class="title">입력하신 이메일로 인증코드를 발송해드립니다.
        coffice에서 사용하시는 이메일로 입력바랍니다.</h5>
              <div class="input-div one">
                 <div class="i">
                    <i class="fas fa-user"></i>
                 </div>
                 <div class="div">
                    <h5>Employee Id</h5>
                    <input type="text" class="input" name="userId">
                 </div>
              </div>
              <div class="input-div pass">
                 <div class="i"> 
                    <i class="fas fa-lock"></i>
                 </div>
                 <div class="div">
                    <h5>Email</h5>
                    <input type="text" class="input" name="email">
                 </div>
              </div>
              <input type="submit" class="btn" value="submit">
            </form>
            
            
        </div>
    </div>
    
    <script src="/js/user/login.js" type="text/javascript"></script>
</body>
</html>