package step.java.exercises;

import oracle.jdbc.internal.OracleStatement;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;

public class Db {
    private final String PREFIX = "KH181_14_";

    private static Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            // Loading config: ../config/db.json
            String connectionString;

            File file = new File("./src/step/java/config/db.json");
            if (!file.exists()) {
                System.out.println("Config location error");
                return null;
            }
            JSONObject conf;
            try (InputStream reader = new FileInputStream(file)) {
                int length = (int) file.length();
                byte[] buf = new byte[length];
                if (length != reader.read(buf)) {
                    throw new IOException("Not all bytes read");
                }
                conf = new JSONObject(new String(buf));
                connectionString = String.format(
                        "jdbc:oracle:thin:%s/%s@%s:%d:XE",
                        conf.getString("user"),
                        conf.getString("pass"),
                        conf.getString("host"),
                        conf.getInt("port")
                );

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                return null;
            }

            try {
                // Registering driver
                DriverManager.registerDriver(
                        // Don't forget add OJDBC.JAR library
                        new oracle.jdbc.driver.OracleDriver()
                );

                // Connecting...
                connection = DriverManager.getConnection(
                        connectionString
                );
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return null;
            }
        }
        return connection;
    }

    public String hash( String str ) {
        if( str == null ) str = "" ;
        try {
            MessageDigest messageDigest =
                    MessageDigest.getInstance( "SHA-1" ) ;
            // MD5 - 128b, SHA-1 - 160b, SHA-2 (SHA256) - 256b
            byte[] src = str.getBytes() ;
            byte[] res = messageDigest.digest( src ) ;
            // return DatatypeConverter.printHexBinary( res ) ;
            char[] sym = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            StringBuilder sb = new StringBuilder();
            for (byte b : res) {
                sb.append(sym[(b & 0xF0) >> 4]);
                sb.append(sym[b & 0xF]);
            }
            return sb.toString();
        } catch( NoSuchAlgorithmException ex ) {
            System.err.println( ex.getMessage() ) ;
            return null ;
        }
    }

    public void login_xe() {
        System.out.println("Login/Password: ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        String[] authData = userInput.split("/");
        System.out.println(authData[0] + " " + authData[1]);

        if( authData.length != 2) {
            System.out.println("Invalid input format");
        } else {
            try (PreparedStatement prep = getConnection().prepareStatement(
                    "SELECT U.* FROM " + PREFIX + "users U WHERE U.login=? "
            )) {
                prep.setString(1, authData[0]);
                ResultSet res = prep.executeQuery();
                if (res.next()) {
                    String salt = res.getString("PASS_SALT");
                    String hash = res.getString("PASS_HASH");
                    String pass = hash(salt + authData[1]);
                    // System.out.println(pass + "\n" + hash);
                    if (pass.equals(hash)) {
                        System.out.printf("Hello, %s", res.getString("NAME"));
                    } else {
                        System.out.println("Incorrect login/password");
                    }
                } else {
                    System.out.println("Login unknown");
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

    public void register_xe() {
        do {
            System.out.println("REGISTRATION");
            System.out.println("Login/Password/Name: ");
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            String[] authData = userInput.split("/");
            System.out.println(authData[0] + " " + authData[1] + authData[2]);

            if (authData.length != 2) {
                System.out.println("Invalid input format");
            } else {
                try (PreparedStatement prep = getConnection().prepareStatement(
                        "SELECT id FROM " + PREFIX + "users WHERE login=?"
                )) {
                    prep.setString(1, authData[0]);
                    ResultSet res = prep.executeQuery();
                    if (res.next()) {
                        if (authData[1].length() > 4) {
                            try (PreparedStatement prep = getConnection().prepareStatement(
                                    "INSERT INTO " + PREFIX + "users (login, name, pass_salt, pass_hash)" +
                                            "VALUES(?, ?, ?, ?)"
                            )) {
                                String name = authData[2];
                                String salt = hash(name);
                                String pass = hash(salt + "123");
                                prep.setString(1, "user1");
                                prep.setString(2, name);
                                prep.setString(3, salt);
                                prep.setString(4, pass);
                                prep.executeUpdate();
                            } catch (SQLException ex) {
                                System.err.println(ex.getMessage());
                            }
                        } else {
                            System.out.println("Password must be bigger than 4 symbols!");
                        }
                    } else {
                        System.out.println("Login already exists");
                    }

                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }

        } while (false);
    }

    public void auth_xe() {
        String query = "";
        try (Statement statement = getConnection().createStatement()) {
            query = "CREATE TABLE " + PREFIX + "users (" +
                    "id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY," +
                    "login NVARCHAR2(64) NOT NULL," +
                    "name NVARCHAR2(64)," +
                    "pass_salt CHAR(40)," +
                    "pass_hash CHAR(40) ) ";

            statement.executeUpdate(query);

            PreparedStatement prep = getConnection().prepareStatement(
              "INSERT INTO " + PREFIX + "users (login, name, pass_salt, pass_hash)" +
                   "VALUES(?, ?, ?, ?)"
            );
            String name = "Petrovich";
            String salt = hash(name);
            String pass = hash(salt + "123");
            prep.setString(1, "user1");
            prep.setString(2, name);
            prep.setString(3, salt);
            prep.setString(4, pass);
            prep.executeUpdate();

            name = "Lukich";
            salt = hash(name);
            pass = hash(salt + "321");
            prep.setString(1, "user2");
            prep.setString(2, name);
            prep.setString(3, salt);
            prep.setString(4, pass);
            prep.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage() + " : " + query);
            return;
        }
    }

    public void auth_maria() {
        // User authorization demo
        // 1. Algo: No password in database
        // 1.1. Hash
        // 1.2. Salt

        String str = hash("123");
        System.out.println(str);
        System.out.println(str.length());
    }

    /**
     * Oracle DB Express Edition
     */
    public void demo_xe() {
        Connection connection;  // ~SqlConnection

        // Loading config: ../config/db.json
        String connectionString;

        File file = new File("./src/step/java/config/db3.json");
        if (!file.exists()) {
            System.out.println("Config location error");
            return;
        }
        JSONObject conf;
        try {
            conf = new JSONObject(
                    new String(
                            new FileInputStream(file)
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

        try {
            // Registering driver
            DriverManager.registerDriver(
                    // Don't forget add OJDBC.JAR library
                    new oracle.jdbc.driver.OracleDriver()
            );

            // Connecting...
            connection = DriverManager.getConnection(
                    connectionString
            );
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return;
        }

        // Queries
        // ! Single DB - user prefixes
        // Update-Queries: DDL + DML (except SELECT)
        String query;
        /*
                query = "CREATE TABLE " + PREFIX + "exercise( "
                + "id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, "
                + "name NVARCHAR2(64) NOT NULL,"
                + "id_parent RAW(16) )";
         */
        /*
        query = "INSERT INTO " + PREFIX + "exercise(name) " +
                "VALUES('Petrovich')";
         */
        /*
        query = "INSERT INTO " + PREFIX + "exercise(name, id_parent) " +
                "VALUES('Lukich', (SELECT id FROM "
                + PREFIX + "exercise WHERE name = 'Petrovich'))";
         */
        /*
        query = String.format(
                "INSERT INTO %sexercise(name, id_parent)" +
                "VALUES('%s', (SELECT id FROM %sexercise WHERE name = '%s'))",
                PREFIX, "Pavlovna", PREFIX, "Petrovich"
        );

        try (Statement statement = connection.createStatement()) {
            // statement ~ SqlCommand
            statement.executeUpdate(query);
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage() + " " + query);
            return;
        }
        */

        // HOMEWORK

        // create cinemas table
        /*
        query = "CREATE TABLE " + "Cinemas" + "exercise( "
                + "id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, "
                + "city NVARCHAR2(50) NOT NULL,"
                + "street NVARCHAR2(50) NOT NULL,"
                + "house NVARCHAR2(10) NOT NULL)";
         */

        // create cinemas' phones table
        /*
        query = "CREATE TABLE " + "Cinemas Phones" + "exercise( "
                + "id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, "
                + "phone NVARCHAR2(30) NOT NULL,"
                + "id_cinema RAW(16))";
         */

        // create countries table
        /*
        query = "CREATE TABLE " + "Countries" + "exercise( "
                + "id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, "
                + "country NVARCHAR2(30) NOT NULL)";
         */

        // create genres table
        /*
        query = "CREATE TABLE " + "Genres" + "exercise( "
                + "id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, "
                + "name NVARCHAR2(25) NOT NULL)";
         */

        // add address
        /*
        query = String.format(
                "INSERT INTO %sexercise(city, street, house)" +
                        "VALUES('%s', '%s','%s')",
                "Cinemas", "Odesa", "Main road", "24"
        );
         */

        // add phone number
        /*
        query = String.format(
                "INSERT INTO %sexercise(phone, id_cinema)" +
                        "VALUES('%s', (SELECT id FROM %sexercise WHERE name = '%s'))",
                "Cinemas Phones", "380508763182", "Cinemas", "PlanetaKino"
        );
         */

        // add country
        /*
        query = String.format(
                "INSERT INTO %sexercise(country)" +
                        "VALUES('%s')",
                "Countries", "Ukraine"
        );
         */

        // add genre
        /*
        query = String.format(
                "INSERT INTO %sexercise(country)" +
                        "VALUES('%s')",
                "Genres", "Action"
        );

        try (Statement statement = connection.createStatement()) {
            // statement ~ SqlCommand
            statement.executeUpdate(query);
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage() + " " + query);
            return;
        }
        */

        /*
        query = String.format(
                "INSERT INTO %sexercise(country)" +
                        "VALUES('%s')",
                "Genres", "Action"
        );
         */

        /*
        query = "INSERT INTO " + "Genres" + "exercise(name) VALUES(?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "Comedy");
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage() + " " + query);
            return;
        }
         */
        /*
        query = "INSERT INTO " + "Countries" + "exercise(country) VALUES(?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "Ukraine");
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage() + " " + query);
            return;
        }
        */
        /*
        query = "INSERT INTO " + PREFIX + "exercise(name) VALUES(?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "Kuzmich");
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage() + " " + query);
            return;
        }
         */

        /*
        query = "SELECT * FROM" + PREFIX + "exercise";
        try (   Statement statement = connection.createStatement();
                ResultSet res = statement.executeQuery(query)
        ) {
            while (res.next()) {
                System.out.printf(
                        "%s\t%s\t%s%n",
                        res.getString(1),
                        res.getString("name"),
                        res.getString(3)
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return;
        }
        */

        System.out.println("OK");
    }

    /**
     * Oracle MariaDB (ex MySQL)
     */
    public void demo_maria() {
        /*
            Preface: 1. Install MySQL/MariaDB
            2. Create DataBase (CREATE DATABASE J181;)
            3. Create ALL PRIVILEGES ON J181
                (GRANT ALL PRIVILEGES ON J181.* TO 'user181'@'localhost' IDENTIFIED BY 'pass181';)
            4.

         */

        String connectionString;
        // Loading config: ../config/db3.json
        File file = new File("./src/step/java/config/db3.json");
        if (!file.exists()) {
            System.out.println("Config location error");
            return;
        }
        JSONObject conf;
        String user, pass;
        try {
            conf = new JSONObject(
                    new String(
                            new FileInputStream(file)
                                    .readAllBytes()
                    )
            );

            connectionString = String.format (
                    "jdbc:%s://%s:%d/%s"   // Размещение БД
                            + "?useUnicode=true&characterEncoding=UTF-8"             // Кодировка канала (подключения)
                            + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",   // При проблемах согласования времени
                    conf.getString( "dbms" ),
                    conf.getString( "host" ),
                    conf.getInt( "port" ),
                    conf.getString( "schema" )
            ) ;
            user = conf.getString("user");
            pass = conf.getString("pass");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return;
        }
        // System.out.println(connectionString);

        // Alternative for driver registering
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver class is not found");
            return;
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    connectionString,
                    user, pass);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return;
        }
        String query;
        /*
                query = "CREATE TABLE " + PREFIX + "exercise( "
                + "id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, "
                + "name NVARCHAR2(64) NOT NULL,"
                + "id_parent RAW(16) )";
         */
        /*
        query = "CREATE TABLE IF NOT EXISTS exercise( "
                + "id BIGINT DEFAULT UUID_SHORT() PRIMARY KEY, "
                + "name VARCHAR(64) NOT NULL,"
                + "id_parent BIGINT )";
         */
        /*
        query = "INSERT INTO exercise(id, name)" +
                "VALUES(uuid_short(), 'Petrovich')";
        */

        /* ! The same table for INSERT and SELECT - error
        query = "INSERT INTO exercise VALUES(uuid_short(), 'Lukich', " +
                "(SELECT id FROM exercise WHERE name = 'Petrovich'))";
         */

        /*
        // 1. Subquery
        String subquery = "SELECT id FROM exercise WHERE name = 'Petrovich'";
        // 2. Prepared query with placeholder
        query = "INSERT INTO exercise VALUES(uuid_short(), 'Lukich', ?)" ;

        try (Statement statement = connection.createStatement();
             PreparedStatement prep = connection.prepareStatement(query)
        ) {
            ResultSet res = statement.executeQuery(subquery);
            if (res.next()) {  // next ~ read - fetch a row
                prep.setString(
                        1,
                        res.getString(1)  // ! indexing origin - from 1 !
                );
                prep.executeUpdate();
            }
           // statement.executeUpdate(query);
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage() + " " + query);
            return;
        }
         */

        // Read-queries
        query = "SELECT * FROM exercise";
        try (   Statement statement = connection.createStatement();
                ResultSet res = statement.executeQuery(query)
        ) {
            while (res.next()) {
                System.out.printf(
                        "%s\t%s\t%s%n",
                        res.getString(1),
                        res.getString("name"),
                        res.getString(3)
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return;
        }

        System.out.println("Maria OK");
    }

    /// HOMEWORK
    public void createUserTable() {

        String query;

        // ORACLE
        /*
        query = "CREATE TABLE " + PREFIX + "users( "
                + "id RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, "
                + "name NVARCHAR2(64) NOT NULL,"
                + "login NVARCHAR2(64) NOT NULL,"
                + "pass_salt NCHAR(40) NOT NULL),"
                + "pass_hash NCHAR(40) NOT NULL)";
        */

        // MySQL
        /*
        query = "CREATE TABLE IF NOT EXISTS Users( "
                + "id BIGINT DEFAULT UUID_SHORT() PRIMARY KEY, "
                + "name VARCHAR(64) NOT NULL,"
                + "login VARCHAR2(64) NOT NULL,"
                + "pass_salt CHAR(40) NOT NULL),"
                + "pass_hash CHAR(40) NOT NULL)";
         */
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
        Кроме диалектов разные СУБД вводят свои типы данных
        VARCHAR2(size) - 4k ANSI
        NVARCHAR2(size)- 4k Localized
        NUMBER(p, s) - p - цифр всего, s (scale) - точность (после .)
        RAW(size) - бинарный (байтовый) тип
        CLOB - Char LOB (~TEXT)
        NCLOB
    СУБД (DBMS) играют роль сервера. Соответственно, требуют подключения.
        При подключении из "языка программирования" требуются специальные драйверы (коннекторы).
    После установки подключения языки программирования предлагают универсальный интерфейс для
        работы с БД (ADO, PDO, JDBC, ...)

 */
