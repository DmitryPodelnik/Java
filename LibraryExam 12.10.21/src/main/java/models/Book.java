package models;

import org.json.JSONObject;

public class Book {
    private String id;
    private String author;
    private String title;
    private String cover;

    public String toJsonString() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("author", author);
        obj.put("title", title);
        obj.put("cover", cover);

        return obj.toString();
    }

    public Book(String author, String title, String cover) {
        this.author = author;
        this.title = title;
        this.cover = cover;
    }

    public Book(String id, String author, String title, String cover) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

