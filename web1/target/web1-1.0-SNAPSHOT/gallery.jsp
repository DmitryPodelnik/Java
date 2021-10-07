<%@ page import="step.java.web1.models.Picture" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="step.java.web1.util.Db" %><%--
  Created by IntelliJ IDEA.
  User: inko1
  Date: 04.10.2021
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="css/gallery.css" />
  <title>Gallery</title>
</head>
<body>
  <h1>Gallery</h1>
  <div>
    <%  ArrayList<Picture> pictures = (ArrayList<Picture>)
            request.getAttribute("pictures");

      if( pictures != null)
      for (Picture item : pictures) { %>
        <div class="picture">
          <img src="uploads/<%= item.getName() %>" alt="<%= item.getName() %>"/>
          <div>
            <p><%= item.getDescription() %></p>
            <p><%= item.getMoment() %></p>
            <tt><%= item.getId() %></tt>
          </div>
          <button class="downlandButton">Download</button>
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

<script src="js/gallery.js"></script>
</html>
