package step.java.web1.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import step.java.web1.models.Picture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class Db {
    final private static String SUFFIX = "_14";
    private static Connection connection;

    public static boolean setConnection(JSONObject connectionData) {
        if (connectionData == null) {
            connection = null;
            return false;
        }

        String connectionString;
        try {
            connectionString = String.format(
                    "jdbc:oracle:thin:%s/%s@%s:%d:XE",
                    connectionData.get("user"),
                    connectionData.get("pass"),
                    connectionData.get("host"),
                    connectionData.get("port")
            );
            DriverManager.registerDriver(
                    new oracle.jdbc.driver.OracleDriver()
            );
            connection =
                    DriverManager.getConnection(connectionString);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public static Connection getConnection() {
        return connection;
    }

    /**
     * Creates table for gallery
     */
    public static void createGallery() {
        if (connection == null) {
            return;
        }
        String query = null;
        try(Statement statement = connection.createStatement()) {
            query = "CREATE TABLE" + "images" + SUFFIX +
                    "(ID RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, " +
                    "Filename NVARCHAR2(256) NOT NULL, " +
                    "Description NVARCHAR(400) NULL, " +
                    "Moment DATE DEFAULT CURRENT_TIMESTAMP)";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            System.err.println("createGallery: " + ex.getMessage() + " " + query);
        }
    }

    /**
     * Inserts Picture in DB
     */
    public static boolean addPicture(Picture picture) {
        if (connection == null) {
            return false;
        }
        String query = "INSERT INTO images" + SUFFIX +
                "(Name, Description) VALUES(?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, picture.getName());
            statement.setString(2, picture.getDescription());
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("addPicture: " + ex.getMessage() + " " + query);
            return false;
        }
    }
}
