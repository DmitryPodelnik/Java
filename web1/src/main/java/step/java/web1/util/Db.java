package step.java.web1.util;

import org.json.simple.JSONObject;
import step.java.web1.models.Picture;
import java.sql.*;
import java.util.ArrayList;

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

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception ignored) {

            }
        }
    }

    /**
     * Creates table for gallery
     */
    private static void createGallery() {
        if (connection == null) {
            return;
        }
        String query = null;
        try(Statement statement = connection.createStatement()) {
            query = "CREATE TABLE" + "Images" + SUFFIX +
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
     * Find picture by id
     */
    public static Picture getPictureById(String id) {
        if (connection == null) {
            return null;
        }

        try(PreparedStatement prep = connection.prepareStatement(
                "SELECT * FROM Pictures" + SUFFIX
                + " WHERE Id = ?"
        )) {
            prep.setString(1, id);
            ResultSet res = prep.executeQuery(); // ссылка на результат
            if (res.next()) {
                return new Picture(
                        res.getString("ID"),
                        res.getString("NAME"),
                        res.getString("DESCRIPTION"),
                        res.getString("MOMENT")
                );
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.err.println("getPictureById: "
                + ex.getMessage());
            return null;
        }
    }

    /**
     * Inserts Picture in DB
     */
    public static boolean addPicture(Picture picture) {
        if (connection == null) {
            return false;
        }
        String query = "INSERT INTO Pictures" + SUFFIX +
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

    public static boolean deletePicture(String id) {
        if (connection == null) {
            return false;
        }
        String query = "DELETE FROM Pictures" + SUFFIX +
                " WHERE Id=(?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("deletePicture: " + ex.getMessage() + " " + query);
            return false;
        }
    }

    public static boolean editPicture(String id, String descr) {
        if (connection == null) {
            return false;
        }
        String query = "UPDATE Pictures" + SUFFIX +
                " SET DESCRIPTION=(?) " +
                " WHERE Id=(?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, descr);
            statement.setString(2, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("deletePicture: " + ex.getMessage() + " " + query);
            return false;
        }
    }

    /**
     * Loads picture(s) list (gallery)
     */
    public static ArrayList<Picture> getPictures() {
        ArrayList<Picture> res = null;
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM Images" + SUFFIX;
            ResultSet answer = statement.executeQuery(query);
            res = new ArrayList<>();
            while (answer.next()) {
                res.add(new Picture(
                        answer.getString("ID"),
                        answer.getString("FILENAME"),
                        answer.getString("DESCRIPTION"),
                        answer.getString("MOMENT")
                ));
            }
        } catch (Exception ex) {
            System.err.println("getPictures: " + ex.getMessage());
        }

        return res;
    }
}
