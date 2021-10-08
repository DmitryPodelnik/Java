<%@ page import="step.java.web1.models.Picture" %>
<<<<<<< HEAD
<%@ page import="step.java.web1.util.Db" %>
<%@ page import="java.util.ArrayList" %><%--
=======
<%@ page import="java.util.ArrayList" %>
<%@ page import="step.java.web1.util.Db" %><%--
>>>>>>> 01a956e5dbad6870f7ca975992d844ff3d772484
  Created by IntelliJ IDEA.
  User: samoylenko_d
  Date: 04.10.2021
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <link rel="stylesheet" href="css/gallery.css" />
  <title>Gallery</title>
</head>
<body>
<<<<<<< HEAD
<h1>Галерея</h1>
<%  Picture[] pictures ;
  try {
    pictures = (Picture[])
            request.getAttribute( "pictures" ) ;
  } catch( ClassCastException ignored ) {
    pictures = new Picture[0] ;
  }
  if( pictures != null )
    for( Picture pic : pictures ) { %>
<div class="picture">
  <img src="uploads/<%= pic.getName() %>" alt="<%= pic.getName() %>" />
  <p><%= pic.getDescription() %></p>
  <i><%= pic.getMoment() %></i>
  <tt><%= pic.getId() %></tt>
  <button>Удалить</button>
  <div class="tool-button tool-download"></div>
=======
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
>>>>>>> 01a956e5dbad6870f7ca975992d844ff3d772484

</div>
<% } %>
<form method="post"
      enctype="multipart/form-data"
      action="" >

  <input type="file" name="galleryfile" />
  <br/>
  <label> Description:
    <textarea name="galleryDescription">Новое изображение</textarea>
  </label>
  <input type="submit" value="Send" />
</form>
<%  String uploadMessage = (String)
        request.getAttribute( "uploadMessage" ) ;
  if( uploadMessage != null ) { %>

<b><%= uploadMessage %></b>

<% } %>
</body>

<script src="js/gallery.js"></script>
<<<<<<< HEAD
</html>
=======
</html>
>>>>>>> 01a956e5dbad6870f7ca975992d844ff3d772484
