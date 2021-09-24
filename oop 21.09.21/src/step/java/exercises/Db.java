package step.java.exercises;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    public void demo() {
        Connection connection;  // ~SqlConnection

        try {
            // Registering driver
            DriverManager.registerDriver(
                    // Don't forget add OJDBC.JAR library
                    new oracle.jdbc.driver.OracleDriver()
            );
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return;
        }
        // Loading config: ../config/db.json
        String connectionString;

        File file = new File("./src/step/java/config/db.json");
        if(!file.exists()) {
            System.out.println("Config location error");
            return;
        }
            JSONObject conf;
            try {
               conf = new JSONObject(
                        new String (
                                new FileInputStream (file)
                                        .readAllBytes()
                        )
                );

                connectionString = String.format(
                        "jdbc:oracle:thin:%s/%s@%s:%d:XE",
                        conf.getString("user"),
                        conf.getString("pass"),
                        conf.getString("host"),
                        conf.getInt("port")
                );
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                return;
            }
        System.out.println(connectionString);
    }
}
/*
    Работа с базами данных.
    База данных - способ организации данных, при котором, кроме данных, создаются связи между ними.
    По видам связей БД классифицируются отдельно.
        Табличные БД (обычныеб, SQL-БД)
        Сетевые БД (Grid)
        Графовые (GraphQL)
        ООП (Oracle)
    Стандарт SQL довольно старый (74-86). Все СУБД его реализуют, а также добавляют свои особенности.
        Это формирует диалекты SQL.
    СУБД (DBMS) играют роль сервера. Соответственно, требуют подключения.
        При подключении из "языка программирования" требуются специальные драйверы (коннекторы).
    После установки подключения языки программирования предлагают универсальный интерфейс для
        работы с БД (ADO, PDO, JDBC, ...)

 */
