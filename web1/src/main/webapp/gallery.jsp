<%@ page import="step.java.web1.models.Picture" %>
<%@ page import="java.util.ArrayList" %><%--
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
  <div>
    <% ArrayList<Picture> pictures =
            (ArrayList<Picture>) request.getAttribute("images");
      for (Picture item : pictures) { %>
        <div>
          <img src="uploads/<%= item.getName() %>" />
          <div>
            <p><%= item.getDescription() %></p>
            <p><%= item.getMoment() %></p>
            <p><%= item.getId() %></p>
          </div>
          <a>Download</a>
        </div>
     <% } %>
  </div>
  <form method="post"
        enctype="multipart/form-data"
        action="" >
    <input type="file" name="galleryfile">
    <br/>
    <label> Description:
      <textarea name="galleryDescription">New image</textarea>
    </label>
    <input type="submit" value="Send">
  </form>
  <% String uploadMessage =
          (String) request.getAttribute("uploadMessage");
     if (uploadMessage != null) { %>

    <b><%= uploadMessage %><b>

  <% } %>

</body>
</html>
