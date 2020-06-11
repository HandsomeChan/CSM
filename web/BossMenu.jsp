<%@ page import="Bean.Users" %><%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/5
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    body{
        font-family: 'Sitka Text';
    }
    #dmenu{
        background: #0a0929;
        background-size:100% 100%;
        margin-top: 10px;
        border-radius: 20px;
    }
    div a{
        color: aliceblue;
        text-align: center;
    }
    .menutable{
        font-size: 18px;
        color: white;
        border-spacing: 60px 5px;
        width:100%;
        text-align: right;
    }
</style>
<div id="dmenu">
    <table class="menutable">
        <tr style="width: 20px">
            <td>
                <a href="MyFoods?type=全部">全部</a>
            </td>
            <td>
                <a href="MyFoods?type=饭类">饭类</a>
            </td>
            <td>
                <a href="MyFoods?type=面类">面类</a>
            </td>
            <td>
                <a href="MyFoods?type=包类">包类</a>
            </td>
            <td>
                <a href="MyFoods?type=饼类">饼类</a>
            </td>
            <td>
                <a href="MyFoods?type=菜类">菜类</a>
            </td>
            <td>
                <a href="MyFoods?type=肉类">肉类</a>
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
