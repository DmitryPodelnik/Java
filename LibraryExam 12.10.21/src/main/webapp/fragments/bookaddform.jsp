<%@ page import="models.Book" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<% Book[] books;
    try {
        books = (Book[])
                request.getAttribute("books");
    } catch (ClassCastException ignored) {
        books = new Book[0];
    }
    if (books != null)
        for (Book book : books) { %>
<div>
    <img src="uploads/<%= book.getTitle() %>" alt="<%= book.getTitle() %>"/>
    <p><%= book.getAuthor() %>
    </p>
    <p><%= book.getTitle() %>
    </p>
    <p><%= book.getId() %>
    </p>

</div>
<% } %>
<div>
    <form method="post">
        <label>Author <input name="author" /></label>
        <br/>
        <label>Title <input name="title" /></label>
        <br/>
        <label>Cover <input type="file" name="cover" /></label>
        <br/>
        <input type="submit" value="Add" />
    </form>
    <% String uploadMessage = (String)
            request.getAttribute("uploadMessage");
        if (uploadMessage != null) { %>

    <b><%= uploadMessage %>
    </b>

    <% } %>
</div>

<!-- JS/FormData variant -->
<div>
    <form method="post"
          enctype="multipart/form-data"
          action="">

        <label>Author <input name="author" /></label>
        <br/>
        <label>Title <input name="title" /></label>
        <br/>
        <label>Cover <input type="file" name="cover" /></label>
        <br/>
        <input type="submit" value="Add" />
    </form>
</div>
