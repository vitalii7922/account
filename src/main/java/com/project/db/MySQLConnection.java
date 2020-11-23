package com.project.db;

import lombok.extern.java.Log;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Log
public class MySQLConnection implements Connection {

    private java.sql.Connection connection = null;

    MySQLConnection() {
        connect();
    }

    @Override
    public void connect() {
        try(FileInputStream fileInputStream = new FileInputStream("src/main/resources/database.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            Class.forName(properties.getProperty("driver.class.name"));
            connection = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.username"),
                    properties.getProperty("db.password"));
        } catch (IOException | SQLException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }
    }

    @Override
    public java.sql.Connection getConnection() {
        return connection;
    }
}
