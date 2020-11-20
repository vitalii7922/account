package com.project.dao;

import com.project.db.DatabaseUtilize;
import com.project.domain.Account;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class AccountDAO {
    private static final AccountDAO accountDao = new AccountDAO();

    private AccountDAO() {
    }

    public static AccountDAO getAccountDao() {
        return accountDao;
    }

    public Account save(Account account) {
        String sql = "INSERT INTO account(first_name, second_name) VALUES(?,?)";
        try (PreparedStatement preparedStatement = DatabaseUtilize.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, account.getFirstName());
            preparedStatement.setString(2, account.getSecondName());
            preparedStatement.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            log.severe(e.getMessage());
        }
        return account;
    }

    public Account findByName(String firstName) {
        Account account = null;
        String sql = "SELECT id, first_name, second_name FROM account WHERE first_name = ?";
        Connection connection = DatabaseUtilize.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            // loop through the result set
            try(ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    account = Account.builder()
                            .id(rs.getLong("id"))
                            .firstName(rs.getString("first_name"))
                            .secondName(rs.getString("second_name"))
                            .build();
                }
            }
        } catch (SQLException e) {
            log.severe(e.getMessage());
        }
        return account;
    }

    public Account updateSecondName(Account account) {
        String query = "UPDATE account SET second_name = ? WHERE first_name = ?";
        try (PreparedStatement preparedStatement = DatabaseUtilize.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, account.getSecondName());
            preparedStatement.setString(2, account.getFirstName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.severe(e.getMessage());
        }
        return account;
    }
}
