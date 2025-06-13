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
  <link rel="stylesheet" type="text/css" href="/css/user/login.css">
</head>
<body>
  <img class="wave" src="/images/wave7.png">
  <div class="container">
    <div class="img">
      <!-- <img style="width:500px;" src="/images/login.png"> -->
    </div>
    <div class="login-content">
    
    
    
      <form action="/user/login" method="post">
      	<img src="/images/login2.png">
        <h2 class="title">Welcome!</h2>
        	<c:if test="${param.error == 'true'}">
        		<div style="color:red;">아이디 또는 비밀번호가 올바르지 않습니다.</div>
    		</c:if>
    		<br>
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
                    <h5>Password</h5>
                    <input type="password" class="input" name="password">
                 </div>
              </div>
              <a href="#">Forgot Password?</a>
              <input type="submit" class="btn" value="Login">
            </form>
            
            
        </div>
    </div>
    
    <script src="/js/user/login.js" type="text/javascript"></script>
</body>
</html>