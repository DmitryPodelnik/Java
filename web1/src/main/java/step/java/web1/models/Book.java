package step.java.web1.models;

import org.json.JSONObject;

public class Book {
    private String author;
    private String title;

    public String toJsonString() {
        JSONObject obj = new JSONObject();
        obj.put("author", author);
        obj.put("title", title);

        return obj.toString();
    }

    public Book(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
