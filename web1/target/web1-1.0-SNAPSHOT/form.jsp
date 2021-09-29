<%--
  Created by IntelliJ IDEA.
  User: inko1
  Date: 29.09.2021
  Time: 12:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String button = request.getParameter("button");
    String loginMessage = "";
    if (button != null) {
        String login = request.getParameter("login");
        if (login == null || login.isEmpty()) {
            loginMessage = "Login shell not be empty";
        }
    }
%>


<html>
<head>
    <title>JSP</title>
</head>
<body>


    <form method="post">
        <div>
            <label>Login:</label>
            <input name="login" />
            <i><%= loginMessage %></i>
        </div>
        <div>
            <label>Password:</label>
            <input type="password" name="password"/>
        </div>
        <div>
            <label>Confirm Password:</label>
            <input type="password" name="confirmPassword"/>
        </div>
        <div>
            <label>Real name:</label>
            <input name="realName" />
        </div>
        <div>
            <input type="submit" value="Register" name="button"/>
        </div>
    </form>

    <%
        String userval = request.getParameter("userval");

    %>
    <p>
        Параметр из формы:
        <% if (userval == null) { %>
        Не передан
        <% } else { %>
        Передан <%= userval %>
        <% } %>
    </p>
</body>
</html>
