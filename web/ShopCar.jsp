<%@ page import="Bean.Users" %>
<%@ page import="java.util.List" %>
<%@ page import="Bean.FoodInfo" %><%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/1
  Time: 16:46
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
        else{
            if (httpSession.getAttribute("user")==null){
        %>
        alert("对不起您尚未登录，请登录后再进行操作！");
        window.location="login.jsp";
        <%
            }
        }
        %>
    </script>
    <link rel="stylesheet" type="text/css" href="firstPage.css">
    <title>我的餐单</title>
</head>
<body>
<jsp:include page="head.jsp"></jsp:include>
<jsp:include page="menu.jsp"></jsp:include>
    <%
        List<FoodInfo> cart=(List) httpSession.getAttribute("cart");
        int sum=0;
        if (request.getAttribute("sum")!=null){
            sum=(int) request.getAttribute("sum");
        }
        if (cart==null){
    %>
<div class="sc">
        <%="您的购物车空空如也，快去首页逛逛吧！"%>
        <a href="PageContent">点此返回首页</a>
    <%
        }
        else{
    %>
    <form action="OrderPage.jsp" method="post">
    <table class="tab">
        <tr>
            <td>  </td>
            <td>食物</td>
            <td>店家</td>
            <td>购买数量</td>
            <td>单价</td>
        </tr>
    <%
            for (int i=0;i<cart.size();i++){
    %>
    <tr>
        <td><input type="checkbox" value="<%=i%>" name="order"
            style="height: 15px;width: 15px"
        /></td>
        <td>
            <%=cart.get(i).getFoodname()%>
        </td>
        <td>
            <%=cart.get(i).getBoss()%>
        </td>
        <td>
            <input type="number" min="0" max="500" value="<%=cart.get(i).getNum()%>"
                   name="numberbox<%=String.valueOf(i)%>"/>
        </td>
        <td>
            <%=cart.get(i).getPrice()%>
        </td>
    </tr>
    <%
            }
    %>
        <tr>
            <td><input type="checkbox" onclick="checkall(this)" name="all"
                       style="height: 15px;width: 15px"/></td>
            <td>全选</td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>
    <input type="submit" class="bb" value="提交订单"/>
        <script>
            function checkall(node) {
                var cb=document.getElementsByName("order");
                for (var i=0;i<cb.length;i++){
                    cb[i].checked=node.checked;
                }
            }
        </script>
    </form>
    <%
        }
    %>
</div>
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
