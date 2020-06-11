<%@ page import="Bean.SoldSort" %>
<%@ page import="Dao.UserDao" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/7
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>所有店家</title>
    <link rel="stylesheet" type="text/css" href="firstPage.css">
</head>
<body>
<jsp:include page="head.jsp"></jsp:include>
<jsp:include page="menu.jsp"></jsp:include>
<%
    List<SoldSort> list= UserDao.getAllboss();
    int pageSize=10;
    int currentpage=Integer.valueOf(request.getParameter("page"));
    int allpages=(int) Math.ceil(list.size()/pageSize);
%>
<div class="sc">
    <%
        if (list==null||list.size()==0){
    %>
    <a href="PageContent">暂无店家记录，点击返回首页</a><br>
    <%
    }
    else {
    %>
    <table style="margin-top:10px;
    margin-bottom:10px;
    border-spacing: 50px 5px;
    text-align: center;
    font-size: 20px;
    margin-left: 25%;
    border: aliceblue 5px solid;
    border-radius: 20px;">
        <tr>
            <td></td>
            <td></td>
            <td>所有店家</td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td>店家</td>
            <td>联系方式</td>
            <td>店铺销量</td>
        </tr>
        <%
            for (int i=(currentpage-1)*pageSize;i<list.size()&&i<currentpage*pageSize;i++){
                SoldSort ss=list.get(i);
        %>
        <tr>
            <td>榜<%=i+1%></td>
            <td><a href="BossShop?name=<%=ss.getBoss()%>"><%=ss.getBoss()%></a></td>
            <td><%=ss.getEmail()%></td>
            <td><%=ss.getNum()%>份</td>
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
       onclick="backaction()" href=<%=currentpage<=1?"#":"booses.jsp?page="+(currentpage-1)%>>上一页</a>
    <a style="margin-left: 7%;font-size: 20px"
       onclick="forwaction()" href=<%=currentpage>allpages?"#":"booses.jsp?page="+(currentpage+1)%>>下一页</a>
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
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
