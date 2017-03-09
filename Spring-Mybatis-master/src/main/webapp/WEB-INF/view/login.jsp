<%--
  Created by IntelliJ IDEA.
  User: David
  Date: 2016/7/16
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h1>登录页面</h1>
<form action="/login" method="post">
    帐号: <input type="text" name="username"><br/>
    密码: <input type="password" name="password"><br/>
    <input type="submit" value="登录">
</form>
</body>
</html>
