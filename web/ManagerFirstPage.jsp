<%@ page import="Bean.Users" %>
<%@ page import="Dao.UserDao" %><%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/8
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <%
    HttpSession httpSession=request.getSession(false);
    if (httpSession==null){
        %>
    <script>
    alert("对不起您尚未登录，请登录后再进行操作哦！");
    window.location="login.jsp";
    </script>
    <%
    }
    if (httpSession.getAttribute("user")==null){
    %>
    <script>
    alert("对不起您尚未登录，请登录后再进行操作！");
    window.location="login.jsp";
</script>
    <%
        }else {
        Users user=(Users)  httpSession.getAttribute("user");
        if (user.getRole()!=2){
            %>
<script>
    alert("对不起您无权访问该页面！");
    window.location="PageContent";
</script>
    <%
            return;
        }
    %>
<head>
    <title>管理员页面</title>
    <link href="firstPage.css" type="text/css" rel="stylesheet">
</head>
<body>
<div class="sc">
    <table style="margin-top:15%;
    border-spacing: 50px 5px;
    text-align: center;
    font-size: 20px;
    margin-left: 38%;
    border: aliceblue 5px solid;
    border-radius: 20px;">
        <tr>
            <td>
                管理员操作清单
            </td>
        </tr>
        <tr>
            <td>
                您好,<%=user.getUname()%>,<a href="logout">点击登出</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="loglogs.jsp?page=1">查看所有用户登录日志</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="buylogs.jsp?page=1">顾客购买日志</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="oplogs.jsp?page=1">所有用户操作日志</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="checkboss.jsp?page=1">查看所有商家销售情况</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="allthings.jsp?page=1">查看各类商品的销售情况</a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="blocked.jsp?page=1">查看所有被封的账号</a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
<%
    }
%>