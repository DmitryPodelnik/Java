<%--
  Created by IntelliJ IDEA.
  User: inko1
  Date: 29.09.2021
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP</title>
</head>
<body>
    <h1>Передача параметров</h1>
    <p>
        Browser (client) -> запрос ->
        Listener (Tomcat) {
            Разбор запроса,
            создание "переменных окружения",
        (request, response)
            запуск приложения
        } -> JVM ->
        Java код {
            анализируем request,
            формируем response
        }
        Java код -> Результат -> Tomcat -> HTTP
        -> Ответ клиенту
    </p>
    <form method="post">
        <input name="userval" />
        <input type="submit" value="Send" />
    </form>
    <p>
        На сегодня стандартизированы 9 методов запроса:
        CONNECT, GET, POST, PUT, HEAD, DELETE,
        PATCH, OPTIONS, TRACE.
        Передача параметров, традиционно, называется "get" и "post", подразумевая
        включение параметров:
            а) в состав URL (адресной строки);
            б) в тело запроса;
    </p>
<% %>
</body>
</html>
