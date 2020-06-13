<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Bean.Foods" %>
<%@ page import="Dao.FoodDao" %>
<%@ page import="Bean.FoodInfo" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
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
<head>
    <title>CSM</title>
    <link rel="stylesheet" type="text/css" href="firstPage.css">
</head>
<body>
    <%@include file="head.jsp"%>
    <%@include file="menu.jsp"%>
    <div style="text-align: center">
    <table class="tb">
    <%
        FoodDao foodDao=new FoodDao();
        UserDao userDao=new UserDao();
        int pageSize=(int) request.getAttribute("pagesize");
        int rowSize=4;
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
                userDao.lookLogs(user.getEmail(),user.getUname(),type,look);
            }
        }
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

                <a href="FoodInfo.jsp?id=<%=f.getId()%>&page=<%=currentpage%>&type=<%=type%>">
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
                        String carUrl="MyCar?id="+f.getId();
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
        String back="PageContent?type="+type+"&page="+((int)request.getAttribute("currentpage")-1);
        String forw="PageContent?type="+type+"&page="+((int)request.getAttribute("currentpage")+1);
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
