package yiarth.raz.java_project.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbManager {

    private Connection db_con;

    public DbManager() {
        connect(); // Method call at each instantiation of DbManager
    }

    /**
     * Method for connecting to database
     */
    private void connect() {
        String properties_location = "src/main/java/yiarth/raz/java_project/config/db.properties"; // database properties location
        Properties properties = new Properties();

        // Get db.properties file from properties_location then load it to properties
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(properties_location)) {
            properties.load(input);
            String url = properties.getProperty("DB_URL");
            String user = properties.getProperty("DB_USER");
            String password = properties.getProperty("DB_PASSWORD");

            db_con = DriverManager.getConnection(url, user, password);
            // System.out.println("Connection Established.");
        } catch (IOException | SQLException e) {
            System.out.println(STR."Error establishing connection: \{e.getMessage()}");
        }
    }

    /**
     * Connection for querying the database
     * @return Connection
     */
    public Connection getConnection() {
        return db_con;
    }

    /**
     * Verify the connection to database
     * @return true/false
     */
    public boolean isConnected() {
        return db_con != null;
    }
}
