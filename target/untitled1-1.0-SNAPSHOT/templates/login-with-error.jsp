<%--
  Created by IntelliJ IDEA.
  User: yevheniikaliukh
  Date: 30.10.2021
  Time: 20:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="/" method="post">
    <table>
        <tr>
            <td></td>
            <td style="color: red">Invalid Credentials</td>
        </tr>
        <tr>
            <td>Your id:</td>
            <td><input type="number" name="username" /></td>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" /></td>
        <tr>
            <td></td>
            <td><input type="submit" value="login" /></td>
        </tr>
    </table>
</form>
</body>
</body>
</html>
