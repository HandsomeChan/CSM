<%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/6/6
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    HttpSession httpSession=request.getSession(false);
    %>
<script>
    <%
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
<%
    if (httpSession.getAttribute("addInfo")!=null){
        %>
<script>
        alert("<%=(String)httpSession.getAttribute("addInfo")%>!!!");
</script>
<%
        httpSession.removeAttribute("addInfo");
    }
%>
<head>
    <link rel="stylesheet" type="text/css" href="firstPage.css">
    <title>添加商品</title>
</head>
<body>
<%@include file="Bosshead.jsp"%>
<%@include file="BossMenu.jsp"%>
<div style="text-align: center">
    <form action="AddFood" method="post" style="text-align: center" enctype="multipart/form-data">
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
                <td>点击右边图标上传实物图:</td>
                <td>
                    <div style="position: relative">
                        <input type="file" accept="image/gif, image/jpg, image/png"
                               style="position: absolute; top: 0; bottom: 0; left: 0;right: 0; opacity: 0;" name="newpic" onchange="showImg(this)"/>
                        <div>
                            <img id="upload" src="pictures/addfood.jpg" width="250" height="250"
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
                <td>名称:</td>
                <td><input class="textbox" type="text" name="fname"></td>
            </tr>
            <tr>
                <td>价格:</td>
                <td><input class="textbox" type="text" name="price"></td>
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
                </select></td>
            </tr>
            <tr>
                <td>货量:</td>
                <td><input value="0" min="0" max="888" type="number" name="add" style="width: 80px">份</td>
            </tr>
            <tr>
                <td>简介:</td>
                <td><input class="textbox" type="text" name="intro"></td>
            </tr>
        </table>
        <input class="bos" type="submit" value="添加美食"/>
    </form>
</div>
<%@include file="foot.jsp"%>
</body>
</html>