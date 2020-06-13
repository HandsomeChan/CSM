<%@ page import="java.util.List" %>
<%@ page import="Bean.FoodInfo" %>
<%@ page import="Dao.FoodDao" %>
<%@ page import="jdk.nashorn.internal.ir.SwitchNode" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="Dao.C3p0Util" %>
<%@ page import="Dao.UserDao" %>
<%@ page import="Bean.Users" %>
<%@ page import="SendEmail.NotifyEmail" %><%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/4
  Time: 15:11
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
        alert("对不起您尚未登录，请登录后再进行操作！");
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
        <%
            if(request.getAttribute("success")!=null){
                boolean success=(boolean)request.getAttribute("success");
                if (success){
                    %>
    <script>
        alert("付款成功！");
    </script>
        <%
                }else {
        %>
    <script>
        alert("付款失败，请重试！");
    </script>
    <%
                }
            }
        %>

    <title>订单消息</title>
    <link rel="stylesheet" type="text/css" href="firstPage.css">
</head>
<body>
<jsp:include page="head.jsp"></jsp:include>
<jsp:include page="menu.jsp"></jsp:include>
<%
    FoodDao foodDao=new FoodDao();
    UserDao userDao=new UserDao();
    String[] list=request.getParameterValues("order");
    List<FoodInfo> cart=(List) httpSession.getAttribute("cart");
    Users us=(Users) httpSession.getAttribute("user");
    double sum=0;
%>
<div class="sc">
<%
    if (list==null||list.length==0){
%>
<a href="ShopCar.jsp">暂无订单消息，点击返回购物车</a><br>
    <%
        }
        else {
    %>
<table style="margin-top:10px;
    margin-bottom:10px;
    border-spacing: 50px 5px;
    text-align: center;
    font-size: 20px;
    margin-left: 30%;
    border: aliceblue 5px solid;
    border-radius: 20px;">
    <tr>
        <td></td>
        <td>确认订单</td>
        <td></td>
    </tr>
    <tr>
        <td>食物</td>
        <td>数量</td>
        <td>单价/元</td>
    </tr>
    <%
        for (int i=0;i<list.length;i++){
            int index=Integer.valueOf(list[i]);
            int actnum=Integer.valueOf(request.getParameter("numberbox"+index));
            cart.get(index).setNum(actnum);
            int rest=foodDao.getRest(cart.get(index).getId());
            if (actnum>rest){
    %>
        <script>
            alert("手慢了！<%=cart.get(index).getFoodname()%>只剩<%=rest%>份了！请返回购物车重新下单！");
            window.location="ShopCar.jsp";
        </script>
    <%
                return;
            }
            if (actnum==0)
                continue;
            sum+=cart.get(index).getPrice()*actnum;
    %>
        <tr>
            <td><%=cart.get(index).getFoodname()%></td>
            <td><%=actnum%></td>
            <td><%=cart.get(index).getPrice()%></td>
        </tr>
    <%
        }
    %>
    <tr>
        <td>账户余额:<%=us.getMoney()%></td>
        <td></td>
        <td>总价:<%=sum%></td>
    </tr>
</table>
<button class="bb" style="margin-left: 0%" onclick="sm()">确认购买</button>
    <%
            httpSession.setAttribute("sum",sum);
            httpSession.setAttribute("orderlist",list);
        }
    %>
</div>
<script>
    function sm() {
        <%
            if (sum>userDao.getMoney(us.getEmail())){
                %>
        alert("余额不足！请先充值！");
        window.location="MyInfo.jsp";
        <%
            }
            else{
        %>
        window.location="OrderJudge";
        <%
            }
        %>
    }
</script>
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
