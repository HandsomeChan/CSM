<%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/2/20
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style>
        body{
            background:url("pictures/webb-dark.png");
            background-size:100% 100%;
        }
        .login{
            margin-top: 10px;
            margin-left: 30%;
            margin-right: 30%;
            padding-top: 3%;
            padding-bottom: 3%;
            background: #0a0929;
            color: white;
            border-radius: 30px;
            font-family:'Sitka Text' ;
            text-align:center;
        }
        .bot{
            width: 200px;
            height: 30px;
            border-radius: 10px;
        }
    </style>
    <script>
        function goTo() {
            window.location="register.jsp";
        }
    </script>
</head>
<body>
    <%@include file="head.jsp"%>
    <%@include file="menu.jsp"%>
    <div class="login">
        <strong style="font-size: 30px;">登录</strong>
        <br>
        <br>
        <form action="login" method="post">
        E-mail
            <input type="email" style="margin-left: 7px" name="email" id="em">
            <br>
            <br>
        密码
            <input type="password" name="password" style="margin-left: 25px" id="pw">
            <br>
            <br>
            <input type="radio" name="role" value="customer">我是顾客
            <input type="radio" name="role" value="boss">我是店家
            <input type="radio" name="role" value="manager">我是管理员
            <br>
            <%  if (request.getAttribute("login_message")!=null){
                out.print((String)request.getAttribute("login_message"));
            }
                %>
            <br>
            <input value="进入" class="bot" id="sb" type="submit">
            <br>
            <br>
            <input type="button" value="返回" class="bot" onclick="window.history.back()">
            <br>
            <br>
            <input type="button" value="没有账号？点此注册" class="bot" onclick="goTo()">
        </form>
    </div>
</body>
</html>
