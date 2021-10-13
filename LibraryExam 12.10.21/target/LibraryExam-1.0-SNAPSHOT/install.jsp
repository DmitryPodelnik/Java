<%@ page import="utils.Db" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
    String confirm = request.getParameter("confirm");
%>
<html>
<head>
    <title>Library</title>
</head>
<body>
    <h1>DB is empty</h1>
    <% if (confirm == null) { %>

        <a href="?confirm=true">INSTALL</a>

    <% } else if (Db.getBookOrm().installTable()) { %>

        <b>CREATED</b>

    <% } else { %>

        <i>CREATE ERROR see server logs</i>

    <% } %>
</body>
</html>
