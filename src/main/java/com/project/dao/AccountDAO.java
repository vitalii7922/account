package com.project.dao;

import com.project.db.Connection;
import com.project.db.ConnectionFactory;
import com.project.db.DatabaseSource;
import com.project.db.MySQLConnectionFactory;
import com.project.domain.Account;
import lombok.extern.java.Log;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class AccountDAO {

    private static AccountDAO accountDAO = new AccountDAO();


    private AccountDAO() {
    }

    public static AccountDAO getAccountDao() {
        return accountDAO;
    }

    public Account save(Account account) {
        String sql = "INSERT INTO account(first_name, last_name) VALUES(?,?)";
        try (PreparedStatement preparedStatement = DatabaseSource.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, account.getFirstName());
            preparedStatement.setString(2, account.getLastName());
            preparedStatement.executeUpdate();
            return account;
        } catch (NullPointerException | SQLException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    public Account findByName(String firstName) {
        Account account = null;
        String sql = "SELECT first_name, last_name FROM account WHERE first_name = ?";
        try (PreparedStatement preparedStatement = DatabaseSource.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    account = Account.builder()
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .build();
                }
            }
        } catch (SQLException e) {
            log.severe(e.getMessage());
        }
        return account;
    }

    public Account updateLastName(String firstName, String lastName) {
        String query = "UPDATE account SET last_name = ? WHERE first_name = ?";
        try (PreparedStatement preparedStatement = DatabaseSource.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.severe(e.getMessage());
        }
        return Account.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    public void deleteAccount(String firstName) {
        String query = "DELETE FROM account WHERE first_name = ?";
        try (PreparedStatement preparedStatement = DatabaseSource.getConnection().prepareStatement(query);) {
            preparedStatement.setString(1, firstName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.severe(e.getMessage());
        }
    }
}
