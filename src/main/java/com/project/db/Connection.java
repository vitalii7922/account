package com.project.db;

public interface Connection {
    void connect();

    java.sql.Connection getConnection();
}
