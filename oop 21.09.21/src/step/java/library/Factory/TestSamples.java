package step.java.library.Factory;

import org.json.JSONObject;
import step.java.library.Book;
import step.java.library.Journal;

public class TestSamples {

    private static JSONObject
            book, journal, invalid, newspaper;

    public static JSONObject getJsonBook() {
        if (book == null) {
            book = new JSONObject();
            book.put("type", "Book");
            book.put("author", "Jack London");
            book.put("title", "Martin Eden");
        }

        return book;
    }

    public static JSONObject getJsonJournal() {
        if (journal == null) {
            journal = new JSONObject();
            journal.put("type", "Journal");
            journal.put("number", "2021, 1");
            journal.put("title", "Quantum Mechanics");
        }

        return journal;
    }

    public static JSONObject getJsonInvalidType() {
        if (invalid == null) {
            invalid = new JSONObject();
            invalid.put("type", "invalid");
            invalid.put("number", "2021, 1");
            invalid.put("title", "Quantum Mechanics");
            invalid.put("author", "Jack London");
        }

        return invalid;
    }

    public static JSONObject getJsonNewspaper() {
        if (newspaper == null) {
            newspaper = new JSONObject();
            newspaper.put("type", "Newspaper");
            newspaper.put("publisher", "Ukrainian Truth");
            newspaper.put("title", "News paper 1");
        }

        return newspaper;
    }
}
