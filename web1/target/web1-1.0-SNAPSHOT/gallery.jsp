<%--
  Created by IntelliJ IDEA.
  User: inko1
  Date: 04.10.2021
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Gallery</title>
</head>
<body>
  <form method="post"
        enctype="multipart/form-data"
        action="" >
    <input type="file" name="galleryfile">
    <input type="submit" value="Send">
  </form>
  <%= request.getAttribute("uploadMessage") %>
</body>
</html>
