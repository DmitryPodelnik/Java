package com.library.lib;

import org.json.JSONObject;

public class Book
        extends Literature {  // extension - inheritance

    // Book( "Martin Eden", "Jack London" )
    private String author;

    public Book(String title, String author) {
        // this.title - error, no access
        // this.setTitle( title ) ;  // warning
        super.setTitle(title);  // OK
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void print() {
        System.out.printf(
                "Book: %s (by %s)",
                super.getTitle(),
                author
        );
    }

    /**
     * @param lit - Literature object
     * @return JSON string if lit is instanceof Book or null
     */
    @Override
    public String toJsonString(Literature lit) {
        if (lit instanceof Book) {
            try {
                Book book = (Book) lit;

                JSONObject obj = new JSONObject();
                obj.put("title", book.getTitle());
                obj.put("author", book.getAuthor());

                return obj.toString();
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.getTitle() + "_" + this.getAuthor();
    }
}
