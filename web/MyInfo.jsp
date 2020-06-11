<%@ page import="Bean.Users" %>
<%@ page import="Bean.Foods" %>
<%@ page import="java.util.List" %>
<%@ page import="Dao.FoodDao" %><%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/2
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人账户</title>
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
<jsp:include page="head.jsp"></jsp:include>
<jsp:include page="menu.jsp"></jsp:include>
<script>
    <%
    if (httpSession.getAttribute("repeat")!=null){
        boolean re=(boolean)httpSession.getAttribute("repeat");
        if (!re){
    %>
    alert("名字重复了！请换一个名字哦！");
    <%
    httpSession.removeAttribute("repeat");
    }
        }
    %>
</script>
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
    httpSession.removeAttribute("suInfo");
    }
    %>
</script>
    <table style=" margin-top:3%;
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
            <td><input type="text" name="uname" value="<%=us.getUname()%>" class="textbox"/></td>
            <td>不改名请勿填写</td>
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
<%
    List<Foods> allfoods= FoodDao.recommend(us.getUname());
    if (allfoods!=null&&allfoods.size()!=0){
%>
<table class="tb">
        <caption><p style="font-size: 20px">猜你喜欢</p></caption>
    <tr>
        <%
            for (int j=0;j<4&&j<allfoods.size();j++){
                Foods f=allfoods.get(j);
        %>
        <td>
            <div>
                <dl>

                    <a href="FoodInfo.jsp?id=<%=f.getId()%>&page=1&type=<%=f.getType()%>">
                        <dd><img src="foods/<%=f.getPicture()%>" width="180" height="180"
                                 style="border: white 5px solid;border-radius: 20px"></dd>
                        <%--                <dd>id:<%=f.getId()%></dd>--%>
                        <dd>店家:<%=f.getBoss()%></dd>
                        <dd>美食名称:<%=f.getFoodname()%></dd>
                        <dd>价格:<%=f.getPrice()%></dd>
                        <dd>已卖出:<%=f.getSold()%>份</dd>
                        <dd>剩余:<%=f.getNum()%>份</dd>
                    </a>
                    <dd>
                        <%
                            Users users=(Users) httpSession.getAttribute("user");
                            httpSession.setAttribute("cp",1);
                            httpSession.setAttribute("ty",f.getType());
                            String loginUrl="login.jsp";
                            String carUrl="/MyCar?id="+f.getId();
                        %>
                        <a name="a1" onclick="check()" href=<%=users==null?loginUrl:carUrl%>><button class="bos">添加到餐单</button></a>
                        <script>
                            function check() {
                                <%
                                    if (users==null){
                                %>
                                alert("对不起您尚未登录，请登录后再进行操作！");
                                <%
                                    }
                                    else {
                                %>
                                alert("添加成功！");

                                <%
                                    }
                                %>
                            }
                        </script>
                    </dd>
                </dl>
            </div>
        </td>
        <%
                }
        %>
    </tr>
</table>
<%
    }
%>
<jsp:include page="foot.jsp"></jsp:include>
</body>
<%
        }
    }
%>
</html>
