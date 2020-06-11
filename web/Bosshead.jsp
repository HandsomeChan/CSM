<%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/5
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    body{
        font-family: 'Sitka Text';
    }
    #dhead{
        color: white;
    }
    div a{
        color: aliceblue;
        text-align: center;
    }
    .headtable{
        text-align: right;
        margin-left: 90%;
    }
</style>
<div id="dhead">
    <a href="MyFoods?type=全部"><img src="pictures/logo.png" width="150" height="120"
                                         style="float: left;margin-left: 85px;margin-top: 0px"></a>
    <table class="headtable" style="text-align: right">
        <tr>
            <td>
                <a href="SoldRecord.jsp?page=1">
                    销售记录
                </a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="BossInfo.jsp">
                    我的账户
                </a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="addFood.jsp">
                    添加商品
                </a>
            </td>
        </tr>
        <tr>
            <td>
                <a href="allBoss.jsp?page=1">
                    店家销售榜单
                </a>
            </td>
        </tr>
    </table>
</div>
