<%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/5/27
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Bean.Users" %>
<%@ page import="java.io.PrintWriter" %>
<style>
    body{
        font-family: 'Sitka Text';
    }
    #dmenu{
        background: #0a0929;
        background-size:100% 100%;
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
                <a href="PageContent?type=精品推荐">精品推荐</a>
            </td>
            <td>
                <a href="PageContent?type=饭类">爱饭</a>
            </td>
            <td>
                <a href="PageContent?type=面类">爱面</a>
            </td>
            <td>
                <a href="PageContent?type=包类">爱包</a>
            </td>
            <td>
                <a href="PageContent?type=饼类">爱饼</a>
            </td>
            <td>
                <a href="PageContent?type=菜类">爱菜</a>
            </td>
            <td>
                <a href="PageContent?type=肉类">爱肉</a>
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
                        out.print("<a href=\"logout\">点击登出</a>");
                    }
                %>
            </td>
        </tr>
    </table>
</div>