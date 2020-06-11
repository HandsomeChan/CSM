<%@ page import="Bean.Users" %><%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/7
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的账户</title>
    <link rel="stylesheet" type="text/css" href="firstPage.css">
    <script>
        <%
        HttpSession httpSession=request.getSession(false);
        if (httpSession==null){
            %>
        alert("对不起您尚未登录，请登录后再进行操作！");
        window.location="login.jsp";
        <%
        }
        else{
            Users us=(Users) httpSession.getAttribute("user");
            if (us==null){
        %>
        alert("对不起您尚未登录，请登录后再进行操作！");
        window.location="login.jsp";
        <%
        }
     %>
    </script>
</head>
<%
    if (us!=null){
%>
<body>
<jsp:include page="Bosshead.jsp"></jsp:include>
<jsp:include page="BossMenu.jsp"></jsp:include>
<script>
    <%
    if (httpSession.getAttribute("suInfo")!=null){
        boolean suc=(boolean)httpSession.getAttribute("suInfo");
        if (suc){
    %>
    alert("修改/充值成功！");
    <%
    }
    else {
    %>
    alert("操作没有成功！！");
    <%
    }
    }
    %>
</script>
<table style=" margin-top:5%;
    margin-bottom:10px;
    border-spacing: 50px 5px;
    text-align: center;
    font-size: 20px;
    margin-left: 23%;
    border: aliceblue 5px solid;
    border-radius: 20px;">
    <form action="ChangeInfo" method="post">
        <tr>
            <td>用户名</td>
            <td><%=us.getUname()%></td>
            <td></td>
        </tr>
        <tr>
            <td>电子邮箱</td>
            <td><%=us.getEmail()%></td>
            <td></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="text" name="pwd" value="<%=us.getPassword()%>" class="textbox"/></td>
            <td>不改密码请勿填写</td>
        </tr>
        <tr>
            <td>剩余金额:<%=us.getMoney()%></td>
            <td><input type="text" name="money" value="0.0" class="textbox"/></td>
            <td><input type="submit" value="一键修改/充值" class="bos"/></td>
        </tr>
    </form>
</table>
<jsp:include page="foot.jsp"></jsp:include>
</body>
<%
        }
    }
%>
</html>
