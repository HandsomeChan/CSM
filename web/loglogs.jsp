<%@ page import="Bean.SoldSort" %>
<%@ page import="Dao.UserDao" %>
<%@ page import="java.util.List" %>
<%@ page import="Bean.Loginlogs" %>
<%@ page import="Bean.Users" %>
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
        Users user=(Users)  httpSession.getAttribute("user");
        if (user.getRole()!=2){
    %>
        <script>
        alert("对不起您无权访问该页面！");
        window.location="PageContent";
        </script>

        <%
            }
        if (httpSession.getAttribute("react")!=null){
            boolean success=(boolean)httpSession.getAttribute("react");
            if (success){
            %>
    <script>
        alert("封号成功！");
    </script>
    <%
                httpSession.removeAttribute("react");
            }
        else {
            %>
    <script>
        alert("封号失败！请重试！");
    </script>
    <%
                httpSession.removeAttribute("react");
            }
        }
        %>
    <title>登录日志</title>
    <link rel="stylesheet" type="text/css" href="firstPage.css">
</head>
<body>
<%
    FoodDao foodDao=new FoodDao();
    UserDao userDao=new UserDao();
    List<Loginlogs> list= userDao.getLogin();
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
    margin-left: 5%;
    border: aliceblue 5px solid;
    border-radius: 20px;">
        <tr>
            <td></td>
            <td></td>
            <td>登录日志</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>联系方式</td>
            <td>用户名</td>
            <td>时间</td>
            <td>ip地址</td>
            <td>身份</td>
            <td>动作</td>
        </tr>
        <%
            for (int i=(currentpage-1)*pageSize;i<list.size()&&i<currentpage*pageSize;i++){
                Loginlogs ll=list.get(i);
        %>
        <tr>
            <td><%=ll.getEmail()%></td>
            <td><%=ll.getUsername()%></td>
            <td><%=ll.getLogintime()%></td>
            <td><%=ll.getIp()%></td>
            <td><%=ll.getRole()%></td>
            <td><%=ll.getAction()%></td>
            <td><a href="Block?uname=<%=ll.getUsername()%>&role=<%=ll.getRole()%>&page=<%=currentpage%>&login=1">
                <button class="bos">封锁账号</button> </a> </td>
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
       onclick="backaction()" href=<%=currentpage<=1?"#":"loglogs.jsp?page="+(currentpage-1)%>>上一页</a>
    <a style="margin-left: 7%;font-size: 20px"
       onclick="forwaction()" href=<%=currentpage>allpages?"#":"loglogs.jsp?page="+(currentpage+1)%>>下一页</a>
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
