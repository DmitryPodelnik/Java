package step.java.web1.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static Connection connection;

    public static boolean setConnection(JSONObject connectionData) {
        String connectionString;
        try {
            new JSONParser().parse(new String(buf));
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

}
