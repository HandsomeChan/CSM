<%--
  Created by IntelliJ IDEA.
  User: HandsomeChen
  Date: 2020/2/20
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Welcome to CSM</title>
  </head>
  <style>
    body{
      background:url("pictures/webb-dark.png");
      background-size:100% 100%;
    }
    #welcome{
      text-align: center;
      margin-top: 200px;
    }
    #bot{
      width: 200px;
      height: 30px;
      border-radius: 10px;
    }
  </style>
  <script>
    function next() {
        window.location="/PageContent";
    }
  </script>
  <body>
  <div id="welcome">
  <strong style="color:white;font-size: 50px;font-family:'Sitka Text'">欢迎光临CSM</strong>
  <p style="font-size: 20px;color: white;font-family:'Sitka Text'">这里总有你喜欢吃的东西</p>
  <button id="bot" type="button" style="font-family: 'Sitka Text'" onclick="next()">进入CSM</button>
  </div>
  </body>
</html>
