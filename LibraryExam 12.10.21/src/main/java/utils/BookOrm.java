package utils;

import models.Book;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

public class BookOrm {
    private Connection connection;
    private final String PREFIX;
    private final JSONObject config;

    public BookOrm(Connection connection, String PREFIX, JSONObject config) {
        this.connection = connection;
        this.PREFIX = PREFIX;
        this.config = config;
    }

    public boolean isTableExists() {
        String query;
        String dbms = config.getString("dbms");
        if (dbms.equalsIgnoreCase("Oracle")) {
            query = "SELECT COUNT(*) " +
                    "FROM USER_TABLES T " +
                    "WHERE T.TABLE_NAME = " +
                    "'" + PREFIX + "BOOKS'";
        } else {
            return false;
        }
        try (
                ResultSet res =
                        connection
                                .createStatement()
                                .executeQuery(query)
        ) {
            if (res.next()) {
                return res.getInt(1) == 1;
            }
        } catch (SQLException ex) {
            System.err.println("BookOrm.IsTableExists(): " + ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean installTable() {
        if (connection == null) {
            return false;
        }
        String query = null;
        try (Statement statement = connection.createStatement()) {
            query = "CREATE TABLE IF NOT EXISTS " + "Books " +
                    "(id BIGINT DEFAULT UUID_SHORT() PRIMARY KEY, " +
                    " Title  VARCHAR(128) NOT NULL, " +
                    " Author VARCHAR2(128) NOT NULL )";
            statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.err.println(
                    "BookOrm.installTable(): " + ex.getMessage() + "\n" + query);
            return false;
        }
    }

    /**
     * Inserts Picture in DB
     */
    public boolean addBook(Book book) {
        if (connection == null) {
            return false;
        }
        String query = "INSERT INTO " + PREFIX + "Books " +
                "(Title, Author, Cover) VALUES(?, ?, ?)";
        try (PreparedStatement statement =
                     connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getCover());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(
                    "addBook: " + ex.getMessage() + " " + query);
            return false;
        }
    }

    /**
     * Loads books(s) list (library)
     */
    public ArrayList<Book> getBooks() {
        if (connection == null) {
            return null;
        }
        ArrayList<Book> res = null;
        int count;
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM " + PREFIX + "Books";
            ResultSet answer = statement.executeQuery(query);
            res = new ArrayList<>();
            while (answer.next()) {
                res.add(new Book(
                        answer.getString("ID"),
                        answer.getString("TITLE"),
                        answer.getString("AUTHOR"),
                        answer.getString("COVER")
                ));
            }
        } catch (Exception ex) {
            System.err.println("getBooks: " + ex.getMessage());
        }
        return res;
    }
}
