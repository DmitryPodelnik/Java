package com.library.factory;

import org.json.JSONObject;

public class TestSamples {

    public static JSONObject getJsonBook() {
        JSONObject book = new JSONObject();
        book.put( "type", "Book");
        book.put( "author", "Jack London");
        book.put( "title", "Martin Eden");

        return book;
    }

    public static JSONObject getJsonJournal() {
        JSONObject journal = new JSONObject();
        journal.put( "type", "Journal" ) ;
        journal.put( "number", "2021, 1" ) ;
        journal.put( "title", "Quantum Mechanics" ) ;
        return journal ;
    }

    public static JSONObject getJsonInvalidType() {
        JSONObject obj = new JSONObject();
        obj.put( "type", "Invalid" ) ;
        obj.put( "number", "2021, 1" ) ;
        obj.put( "title", "Quantum Mechanics" ) ;
        obj.put( "author", "Jack London");
        return obj ;
    }
}
