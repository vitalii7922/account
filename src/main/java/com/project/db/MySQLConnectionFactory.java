package com.project.db;

public class MySQLConnectionFactory implements ConnectionFactory {
    @Override
    public Connection getDataSource() {
        return new MySQLConnection();
    }
}
