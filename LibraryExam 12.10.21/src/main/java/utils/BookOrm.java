package utils;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            query = "CREATE TABLE " + PREFIX + "Books " +
                    "(Id     RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, " +
                    " Title  NVARCHAR2(128) NOT NULL, " +
                    " Author NVARCHAR2(128) NOT NULL, " +
                    " Cover  NVARCHAR2(128) )";
            statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.err.println(
                    "BookOrm.installTable(): " + ex.getMessage() + "\n" + query);
            return false;
        }
    }
}
