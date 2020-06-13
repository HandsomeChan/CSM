<%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/5/28
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <style>
        body{
            background:url("pictures/webb-dark.png");
            background-size:100% 100%;
        }
        .register{
            margin-top: 5px;
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
        .bott{
            width: 200px;
            height: 30px;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<%@include file="head.jsp"%>
<%@include file="menu.jsp"%>
<div class="register">
    <strong style="font-size: 30px;">注册</strong>
    <br>
    <br>
    <form action="regist" method="post">
        E-mail
        <input type="email" style="margin-left: 15px" name="email">
        <br>
        <br>
        用户名
        <input type="text" style="margin-left: 15px" name="username">
        <br>
        <br>
        密码
        <input type="password" name="password" style="margin-left: 30px">
        <br>
        <br>
        确认密码
        <input type="password" name="password-again" style="margin-left: 0px">
        <br>
        <br>
        <input type="radio" name="role" value="customer">我是顾客
        <input type="radio" name="role" value="boss">我是店家
        <br>
        <%  if (request.getAttribute("register_message")!=null){
            out.print((String)request.getAttribute("register_message"));
        }
        %>
        <br>
        <input type="submit" value="提交" class="bott">
        <br>
        <br>
        <input type="button" value="返回" class="bott" onclick="window.history.back()">
        <br>
        <br>
    </form>
</div>
</body>
</html>
