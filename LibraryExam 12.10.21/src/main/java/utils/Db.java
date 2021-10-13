package utils;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {
    final private static String SUFFIX = "_14";
    private static JSONObject config ;
    private static Connection connection ;

    public static Connection getConnection() {
        return connection;
    }

    public static boolean setConnection(JSONObject json) {
        try {
            String connectionString;
            String dbms = json.getString("dbms");
            if (dbms.equalsIgnoreCase("Oracle")) {
                connectionString = String.format(
                        "jdbc:oracle:thin:%s/%s@%s:%s:XE",
                        json.getString("user"),
                        json.getString("pass"),
                        json.getString("host"),
                        json.getString("port")
                );
                DriverManager.registerDriver(
                        new oracle.jdbc.driver.OracleDriver()
                );
                connection =
                        DriverManager.getConnection(connectionString);

                config = json;
                return true;
            }
            else if (dbms.equalsIgnoreCase("Oracle")) {

            } else {
                System.err.println("Db: Unsupported DBMS");
            }
        } catch (Exception ex) {
            System.err.println("Db: " + ex.getMessage());
        }
        connection = null;
        config = null;
        return false;
    }

    public static void closeConnection() {
        if (connection != null)
            try {
                connection.close();
            } catch (Exception ignored) {

            }
    }

    /**
     * Creates table for gallery
     */
    private static void createLibrary() {
        if (connection == null) return;
        String query = null;
        try (Statement statement = connection.createStatement()) {
            query = "CREATE TABLE Books" + SUFFIX +
                    "(Id          RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, " +
                    " Title        NVARCHAR2(256) NOT NULL, " +
                    " Author NVARCHAR2(256) NOT NULL, " +
                    " Moment      DATE DEFAULT CURRENT_TIMESTAMP )";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            System.err.println(
                    "createLibrary: " + ex.getMessage() + " " + query);
        }
    }
}
