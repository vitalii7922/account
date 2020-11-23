package com.project.db;

import lombok.extern.java.Log;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * used to connect to the database
 *
 * a connection is executed with a property file
 */
@Log
public class DatabaseSource {
    private static final String DB_DRIVER_CLASS = "driver.class.name";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_URL = "db.url";
    private static Connection connection = null;


    private DatabaseSource() {
    }

    static {
        try(FileInputStream fileInputStream = new FileInputStream("src/main/resources/database.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            Class.forName(properties.getProperty(DB_DRIVER_CLASS));
            connection = DriverManager.getConnection(properties.getProperty(DB_URL), properties.getProperty(DB_USERNAME),
                    properties.getProperty(DB_PASSWORD));
        } catch (IOException | SQLException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }
    }


    public static Connection getConnection() {
        return connection;
    }
}
