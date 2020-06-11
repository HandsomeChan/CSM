<%@ page import="Bean.Foods" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/5
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script>
        <%
        HttpSession httpSession=request.getSession(false);
        if (httpSession==null){
            %>
        alert("对不起您尚未登录，请登录后再进行操作哦！");
        window.location="login.jsp";
        <%
        }
        if (httpSession.getAttribute("user")==null){
        %>
        alert("对不起您尚未登录，请登录后再进行操作！");
        window.location="login.jsp";
        <%
            }else {

        %>
    </script>
    <script>
        <%
            if (httpSession.getAttribute("success")!=null){
                boolean success=(boolean)httpSession.getAttribute("success");
                if (success){
        %>
        alert("添加成功！");
        <%
        httpSession.removeAttribute("success");
        }
            else{
        %>
        alert("添加时出现错误！请重试！");
        <%
        httpSession.removeAttribute("success");
            }
        }
        %>
    </script>
    <script>
        <%
            if (httpSession.getAttribute("dele")!=null){
                boolean success=(boolean)httpSession.getAttribute("dele");
                if (success){
        %>
        alert("成功删除！");
        <%
        httpSession.removeAttribute("dele");
        }
            else{
        %>
        alert("删除时出现错误！请重试！");
        <%
        httpSession.removeAttribute("dele");
            }
        }
        %>
    </script>
    <title>我的店铺</title>
    <link rel="stylesheet" type="text/css" href="firstPage.css">
</head>
<body>
<%@include file="Bosshead.jsp"%>
<%@include file="BossMenu.jsp"%>
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

                <a href="BossFoodInfo.jsp?id=<%=f.getId()%>">
                    <dd><img src="foods/<%=f.getPicture()%>" width="180" height="180"
                             style="border: white 5px solid;border-radius: 20px"></dd>
                    <%--                <dd>id:<%=f.getId()%></dd>--%>
                    <dd>名称:<%=f.getFoodname()%></dd>
                    <dd>价格:<%=f.getPrice()%></dd>
                    <dd>已卖出:<%=f.getSold()%>份</dd>
                    <dd>剩余:<%=f.getNum()%>份</dd>
                </a>
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
    String back="MyFoods?type="+type+"&page="+((int)request.getAttribute("currentpage")-1);
    String forw="MyFoods?type="+type+"&page="+((int)request.getAttribute("currentpage")+1);
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
<%
    }
%>
<%@include file="foot.jsp"%>
</body>
</html>
