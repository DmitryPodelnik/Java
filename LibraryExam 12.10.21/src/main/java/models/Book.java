package models;

import org.json.JSONObject;

public class Book {
    private String id;
    private String author;
    private String title;
    private String moment;

    public String toJsonString() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("author", author);
        obj.put("title", title);
        obj.put("moment", moment);

        return obj.toString();
    }

    public Book(String id, String author, String title, String moment) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.moment = moment;
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

    public String getMoment() {
        return moment;
    }

    public void setMoment(String moment) {
        this.moment = moment;
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

