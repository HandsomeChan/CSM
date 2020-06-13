<%@ page import="Bean.SoldSort" %>
<%@ page import="Dao.UserDao" %>
<%@ page import="java.util.List" %>
<%@ page import="Dao.FoodDao" %><%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/7
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        HttpSession httpSession=request.getSession(false);
        if (request.getParameter("page")==null){
    %>
    <script>
        alert("操作有误，将回到首页！");
        window.location="PageContent";
    </script>
    <%
        }
        int currentpage=Integer.valueOf(request.getParameter("page"));
        if (httpSession==null){
    %>
    <script>
        alert("对不起您尚未登录，请登录后再进行操作！");
        window.location="login.jsp";
    </script>
    <%
    }
    else{
        if (httpSession.getAttribute("user")==null){
    %>
    <script>
        alert("对不起您尚未登录，请登录后再进行操作！");
        window.location="login.jsp";
    </script>
    <%
    }
    else {
    %>
    <title>店家销售榜单</title>
    <link rel="stylesheet" type="text/css" href="firstPage.css">
</head>
<body>
<jsp:include page="Bosshead.jsp"></jsp:include>
<jsp:include page="BossMenu.jsp"></jsp:include>
<%
    FoodDao foodDao=new FoodDao();
    UserDao userDao=new UserDao();
    List<SoldSort> list= userDao.getAllboss();
    int pageSize=10;
    int allpages=(int) Math.ceil(list.size()/pageSize);
%>
<div class="sc">
    <%
        if (list==null||list.size()==0){
    %>
    <a href="MyFoods">暂无店家记录，点击返回首页</a><br>
    <%
    }
    else {
    %>
    <table style="margin-top:10px;
    margin-bottom:10px;
    border-spacing: 50px 5px;
    text-align: center;
    font-size: 20px;
    margin-left: 27%;
    border: aliceblue 5px solid;
    border-radius: 20px;">
        <tr>
            <td></td>
            <td></td>
            <td>店家销量榜单</td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td>店家</td>
            <td>卖出数量</td>
            <td>总收入</td>
        </tr>
        <%
            for (int i=(currentpage-1)*pageSize;i<list.size()&&i<currentpage*pageSize;i++){
                SoldSort ss=list.get(i);
        %>
        <tr>
            <td>榜<%=i+1%></td>
            <td><%=ss.getBoss()%></td>
            <td><%=ss.getNum()%>份</td>
            <td><%=ss.getEarn()%>元</td>
        </tr>
        <%
            }
        %>
    </table>
    <%
        }
    %>
</div>
<div>
    <a style="margin-left: 42%;font-size: 20px"
       onclick="backaction()" href=<%=currentpage<=1?"#":"allBoss.jsp?page="+(currentpage-1)%>>上一页</a>
    <a style="margin-left: 7%;font-size: 20px"
       onclick="forwaction()" href=<%=currentpage>allpages?"#":"allBoss.jsp?page="+(currentpage+1)%>>下一页</a>
</div>
<script>
    function backaction() {
        <%
        if (currentpage<=1){
        %>
        alert("已经是首页了！");
        <%
        }
        %>
    }
    function forwaction() {
        <%
        if (currentpage>allpages){
        %>
        alert("已经是最后一页了！");
        <%
        }
        %>
    }
</script>
<%
        }
    }
%>
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
