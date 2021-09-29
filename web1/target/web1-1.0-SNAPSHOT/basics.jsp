<%--
  Created by IntelliJ IDEA.
  User: inko1
  Date: 29.09.2021
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Основы JSP</title>
    <% int x = 10; %>
    x = <b><%= x %></b>
    <br/>
    <% if (x < 20) { %>
        <i>Less than 20</i>
    <% } else { %>
        <i>Not less than 20</i>
    <% } %>
</head>
<body>

</body>
</html>
