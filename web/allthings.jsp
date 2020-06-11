<%@ page import="Bean.Users" %>
<%@ page import="Bean.Foods" %>
<%@ page import="Dao.FoodDao" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/8
  Time: 21:04
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
        Users user=(Users)  httpSession.getAttribute("user");
        if (user.getRole()!=2){
    %>
    <script>
        alert("对不起您无权访问该页面！");
        window.location="PageContent";
    </script>
    <%
        }
    %>
    <title>所有商品</title>
    <link rel="stylesheet" type="text/css" href="firstPage.css">
</head>
<body>
<%
    List<Foods> list= FoodDao.ManGetfoods();
    int pageSize=20;
    int allpages=(int) Math.ceil(list.size()/pageSize);
%>
<div class="sc">
    <%
        if (list==null||list.size()==0){
    %>
    <a href="ManagerFirstPage.jsp">暂无记录，点击返回首页</a><br>
    <%
    }
    else {
    %>
    <table style="margin-top:10px;
    margin-bottom:10px;
    border-spacing: 50px 5px;
    text-align: center;
    font-size: 20px;
    margin-left: 15%;
    border: aliceblue 5px solid;
    border-radius: 20px;">
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td>所有商品</td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>食品名</td>
            <td>类别</td>
            <td>店家</td>
            <td>价格</td>
            <td>已卖出</td>
            <td>剩余</td>
        </tr>
        <%
            for (int i=(currentpage-1)*pageSize;i<list.size()&&i<currentpage*pageSize;i++){
                Foods f=list.get(i);
        %>
        <tr>
            <td><%=f.getFoodname()%></td>
            <td><%=f.getType()%></td>
            <td><%=f.getBoss()%></td>
            <td><%=f.getPrice()%>元</td>
            <td><%=f.getSold()%>份</td>
            <td><%=f.getNum()%>份</td>
            <td><a href="javascript:if(confirm('确实要下架改商品吗?'))location='DelectFood?id=<%=f.getId()%>&page=<%=currentpage%>'">
                <button class="bos">下架该商品</button> </a></td>
        </tr>
        <%
            }
        %>
    </table>
    <a href="ManagerFirstPage.jsp"><button class="bos">返回首页</button></a>
    <%
        }
    %>
</div>
<div>
    <a style="margin-left: 42%;font-size: 20px"
       onclick="backaction()" href=<%=currentpage<=1?"#":"allthings.jsp?page="+(currentpage-1)%>>上一页</a>
    <a style="margin-left: 7%;font-size: 20px"
       onclick="forwaction()" href=<%=currentpage>allpages?"#":"allthings.jsp?page="+(currentpage+1)%>>下一页</a>
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
</body>
</html>
