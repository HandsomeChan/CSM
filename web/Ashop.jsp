<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Bean.Foods" %>
<%@ page import="Dao.FoodDao" %>
<%@ page import="Bean.FoodInfo" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="Bean.Users" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="Dao.UserDao" %>
<%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/5/27
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    #dmenu{
        background: #0a0929;
        background-size:100% 100%;
        border-radius: 20px;
    }
    .menutable{
        font-size: 18px;
        color: white;
        border-spacing: 60px 5px;
        width:100%;
        text-align: right;
    }
</style>
<head>
    <title>欢迎来到我的店铺</title>
    <link rel="stylesheet" type="text/css" href="firstPage.css">
</head>
<body>
<%@include file="head.jsp"%>
<%
    FoodDao foodDao=new FoodDao();
    UserDao userDao=new UserDao();
    String boss=(String) request.getAttribute("boss");
    HttpSession httpSession=request.getSession();
    if (httpSession.getAttribute("user")!=null){
        Users u=(Users) httpSession.getAttribute("user");
        if (httpSession.getAttribute("time")!=null&&httpSession.getAttribute("ftype")!=null) {
            LocalDateTime time = LocalDateTime.now();
            int begin=(int) httpSession.getAttribute("time");
            String type=(String) httpSession.getAttribute("ftype");
            httpSession.removeAttribute("time");
            httpSession.removeAttribute("ftype");
            int end = time.getSecond();
            int look=end-begin;
            userDao.lookLogs(u.getEmail(),u.getUname(),type,look);
        }
    }
%>
<div id="dmenu">
    <table class="menutable">
        <tr style="width: 20px">
            <td>
                <a href="BossShop?name=<%=boss%>&type=全部"><%=boss%>的商品</a>
            </td>
            <td>
                <a href="BossShop?name=<%=boss%>&type=饭类">爱饭</a>
            </td>
            <td>
                <a href="BossShop?name=<%=boss%>&type=面类">爱面</a>
            </td>
            <td>
                <a href="BossShop?name=<%=boss%>&type=包类">爱包</a>
            </td>
            <td>
                <a href="BossShop?name=<%=boss%>&type=饼类">爱饼</a>
            </td>
            <td>
                <a href="BossShop?name=<%=boss%>&type=菜类">爱菜</a>
            </td>
            <td>
                <a href="BossShop?name=<%=boss%>&type=肉类">爱肉</a>
            </td>
            <td>
                <%
                    HttpSession session1=request.getSession();
                    Users user=(Users) session1.getAttribute("user");
                    if (user==null){
                        out.print("未登录，");
                        out.print("<a href=\"login.jsp\">请先登录</a>");
                    }
                    else {
                        out.print("欢迎您，" + user.getUname()+"，");
                        out.print("<a href=\"/logout\">点击登出</a>");
                    }
                %>
            </td>
        </tr>
    </table>
</div>
<div style="text-align: center">
    <table class="tb">
        <%
            int pageSize=(int) request.getAttribute("pagesize");
            int rowSize=4;
            int currentpage=(int) request.getAttribute("currentpage");
            String type=(String) request.getAttribute("type");
            ArrayList<Foods> allfoods=(ArrayList<Foods>)request.getAttribute("foods");
            if (allfoods==null||allfoods.size()==0){
                out.print("没有更多内容啦！去别处逛逛吧！");
            }
            else {
                for (int i=0;i<allfoods.size();i+=rowSize){
        %>
        <tr>
            <%
                for (int j=0;j<rowSize&&i+j<allfoods.size();j++){
                    Foods f=allfoods.get(i+j);
            %>
            <td>
                <div>
                    <dl>

                        <a href="FoodInfo.jsp?id=<%=f.getId()%>&page=<%=currentpage%>&boss=<%=f.getBoss()%>&type=<%=type%>">
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
                                httpSession.setAttribute("cp",currentpage);
                                httpSession.setAttribute("ty",type);
                                String loginUrl="login.jsp";
                                String carUrl="MyCar?id="+f.getId()+"&boss="+boss+"&page="+currentpage+"&type="+type;
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
                }
            %>
        </tr>
        <%
            }
        %>
    </table>
</div>
<%
    String back="BossShop?name="+boss+"&type="+type+"&page="+((int)request.getAttribute("currentpage")-1);
    String forw="BossShop?name="+boss+"&type="+type+"&page="+((int)request.getAttribute("currentpage")+1);
    int allpages=(int) request.getAttribute("pages");
%>
<div>
    <a style="margin-left: 42%;font-size: 20px"
       onclick="backaction()" href=<%=currentpage<=1?"#":back%>>上一页</a>
    <a style="margin-left: 7%;font-size: 20px"
       onclick="forwaction()" href=<%=currentpage>allpages?"#":forw%>>下一页</a>
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
<%@include file="foot.jsp"%>
</body>
</html>
