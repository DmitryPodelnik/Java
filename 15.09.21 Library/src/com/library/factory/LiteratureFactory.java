package com.library.factory;

import com.library.lib.Book;
import com.library.lib.Journal;
import com.library.lib.Literature;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class LiteratureFactory {
    /**
     * Collection of factories for concrete types
     */
    private ArrayList<ConcreteFactory> factories;

    public LiteratureFactory() {
        factories = new ArrayList<>();
    }

    /**
     * Registerer for concrete factories
     *
     * @param factory the factory
     * @return true if register OK, false if errors or factory already registered
     */
    public boolean registerFactory(ConcreteFactory factory) {
        if (factories.contains(factory)) {
            return false;
        }
        factories.add(factory);
        return true;
    }

    /**
     * Creates concrete Literature (Book, Journal,...) from JSON Object
     *
     * @param obj JSON Object with concrete fields
     * @return concrete Literature
     */
    Literature createFrom(JSONObject obj) {

        try {
            if (obj.getString("type") == "Book") {
                return new Book(
                        obj.getString("title"),
                        obj.getString("author")
                );
            } else if (obj.getString("type") == "Journal") {
                return new Journal(
                        obj.getString("title"),
                        obj.getString("number")
                );
            }
        } catch (Exception ex) {
            System.out.println("Cannot create literature from JSON obj.");
        }
        return null;
    }

    public Literature createFrom(File file) {
        if (file == null) {
            return null;
        }

        try (InputStream reader = new FileInputStream(file)) {
            int sym;
            StringBuilder sb = new StringBuilder();

            while ((sym = reader.read()) != -1) {
                sb.append((char) sym);
            }

            return this.createFrom(new JSONObject(sb.toString()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }


}

