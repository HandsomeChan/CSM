<%@ page import="Bean.Foods" %>
<%@ page import="Dao.FoodDao" %>
<%@ page import="Dao.UserDao" %><%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/5
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        HttpSession ses=request.getSession();
        %>
    <script>
        <%
            if (ses.getAttribute("success")!=null){
                boolean success=(boolean)ses.getAttribute("success");
                if (success){
        %>
        alert("修改成功！");
        <%
        ses.removeAttribute("success");
        }
            else{
        %>
        alert("修改错误！请重试！");
        <%
        ses.removeAttribute("success");
            }
        }
        %>
    </script>
    <%
        String sid=request.getParameter("id");
        int id=0;
        if (sid==null&&ses.getAttribute("fid")==null){
            response.sendRedirect("MyFoods");
    %>
    <%
    }else {
            if (sid==null)
                id=(int) ses.getAttribute("fid");
            else
                id=Integer.valueOf(sid);
        FoodDao foodDao=new FoodDao();
        UserDao userDao=new UserDao();
        Foods food= foodDao.getFood(id);
        Users u=(Users) ses.getAttribute("user");
        ses.setAttribute("fid",id);
    %>
    <link rel="stylesheet" type="text/css" href="firstPage.css">
    <title><%=food.getFoodname()%></title>
</head>
<body>
<%@include file="Bosshead.jsp"%>
<%@include file="BossMenu.jsp"%>
<div style="text-align: center">
    <form action="ChangeFoodInfo" method="post" style="text-align: center" enctype="multipart/form-data">
        <table style="
            margin-left: 350px;
            margin-top: 10px;
            margin-bottom:10px;
            border-spacing: 50px 5px;
            text-align: center;
            border: aliceblue 5px solid;
            border-radius: 20px;
           font-size: 18px;">
            <tr style="border: aliceblue 3px solid">
                <td>点击图片选择新的实物图:</td>
            <td>
                <div style="position: relative">
                    <input type="file" accept="image/gif, image/jpg, image/png"
                           style="position: absolute; top: 0; bottom: 0; left: 0;right: 0; opacity: 0;" name="newpic" onchange="showImg(this)"/>
                    <div>
                <img id="upload" src="foods/<%=food.getPicture()%>" width="250" height="250"
            style="border: white 5px solid;border-radius: 30px;margin-right: 40px;margin-left: 20px">
                    </div>
                </div>
                <script>
                    function showImg(input) {
                        var file = input.files[0];
                        var reader = new FileReader();
                        // 图片读取成功回调函数
                        reader.onload = function(e) {
                            document.getElementById('upload').src=e.target.result
                        }
                        reader.readAsDataURL(file);
                    }
                </script>
            </td>
            </tr>
        <tr>
            <td>修改名称:</td>
            <td><input value="<%=food.getFoodname()%>" class="textbox" type="text" name="fname"></td>
        </tr>
        <tr>
            <td>价格:</td>
            <td><input value="<%=food.getPrice()%>" class="textbox" type="text" name="price"></td>
        </tr>
        <tr>
            <td>已卖出:</td>
            <td><%=food.getSold()%>份</td>
        </tr>
            <tr>
                <td>库存量:</td>
                <td><%=food.getNum()%>份</td>
            </tr>
        <tr>
            <td>类别:</td>
            <td><select name="type" style="height: 30px;width: 80px">
                <option value="饭类">饭类</option>
                <option value="面类">面类</option>
                <option value="包类">包类</option>
                <option value="饼类">饼类</option>
                <option value="菜类">菜类</option>
                <option value="肉类">肉类</option>
                <option value="<%=food.getType()%>" selected="selected"><%=food.getType()%></option>
            </select></td>
        </tr>
        <tr>
            <td>补货:</td>
            <td><input value="0" min="0" max="888" type="number" name="add" style="width: 80px">份</td>
        </tr>
        <tr>
            <td>简介:</td>
            <td><input value="<%=food.getIntroduction()%>" class="textbox" type="text" name="intro"></td>
        </tr>
    </table>
        <input class="bos" type="submit" value="一键修改"/>
    </form>
    <a href="javascript:if(confirm('确实要删除吗?'))location='DelectFood?id=<%=id%>'"><button class="bos" >删除</button></a>
</div>
<%--<script>--%>
<%--    function check() {--%>
<%--        <%--%>
<%--            if (u==null){--%>
<%--        %>--%>
<%--        alert("对不起您尚未登录，请登录后再进行操作！");--%>
<%--        <%--%>
<%--            }--%>
<%--            else {--%>
<%--        %>--%>
<%--        alert("添加成功！");--%>
<%--        <%--%>
<%--            }--%>
<%--        %>--%>
<%--    }--%>
<%--</script>--%>
<%
    }
%>
<%@include file="foot.jsp"%>
</body>
</html>
