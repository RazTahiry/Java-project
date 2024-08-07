package com.yiarth.java_project.config;

import com.yiarth.java_project.MainApplication;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbManager {

    private Connection db_con;

    public DbManager() {
        connect(); // Function call for every instantiation of the class DbManager
    }

    /**
     * Method for connecting to database
     */
    private void connect() {
        Properties properties = new Properties();
        final String DB_PROPERTIES = "properties/db.properties"; // Database properties in resources

        try (InputStream input = MainApplication.class.getResourceAsStream(DB_PROPERTIES)) {
            if (input != null) {
                // Load the database properties then parse it
                properties.load(input);
                String url = properties.getProperty("DB_URL");
                String user = properties.getProperty("DB_USER");
                String password = properties.getProperty("DB_PASSWORD");

                db_con = DriverManager.getConnection(url, user, password);
                // System.out.println("Connection Established.");
                if (db_con == null) {
                    throw new SQLException("Not able to get connection from DriverManager!");
                }
            } else {
                throw new IOException("Database configuration file not found!");
            }
        } catch (IOException | SQLException e) {
            System.out.println(STR."Error establishing connection: \{e.getMessage()}");
        }
    }

    /**
     * Connection for querying the database
     * @return connection
     */
    public Connection getConnection() {
        return db_con;
    }

    /**
     * Verify the connection to database
     * @return {@code true} if the connection with database is established,
     * otherwise {@code false}
     */
    public boolean isConnected() {
        return db_con != null;
    }
}
