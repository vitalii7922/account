package com.project.dao;

import com.project.db.DatabaseSource;
import com.project.domain.Account;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * responsible for interacting with database
 */
@Log
public class AccountDAO {

    private static AccountDAO accountDAO = new AccountDAO();

    private AccountDAO() {
    }

    public static AccountDAO getAccountDao() {
        return accountDAO;
    }

    //get connection to datasource
    private java.sql.Connection connection =  DatabaseSource.getConnection();

    /**
     * save an account to DB
     *
     * @param account account
     * @return saved account or null if exception's thrown
     */
    public Account save(Account account) {
        String sql = "INSERT INTO account(first_name, last_name) VALUES(?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, account.getFirstName());
            preparedStatement.setString(2, account.getLastName());
            preparedStatement.executeUpdate();
            log.info(String.format("saved account with fields %s %s", account.getFirstName(), account.getLastName()));
            return account;
        } catch (NullPointerException | SQLException e) {
            log.severe(e.getMessage());
        }
        return null;
    }

    /**
     *find an account in DB
     *
     * @param firstName first name of an entity
     * @return account entity or null if an entity with specified name not found
     */
    public Account findByName(String firstName) {
        Account account = null;
        String sql = "SELECT first_name, last_name FROM account WHERE first_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    account = Account.builder()
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .build();
                }
            }
            log.info(String.format("found an account %s", firstName));
        } catch (SQLException e) {
            log.severe(e.getMessage());
        }
        return account;
    }

    /**
     * update last name of an entity
     *
     * @param firstName first name of an entity
     * @param lastName last name of an entity
     * @return updated entity
     */
    public Account updateLastName(String firstName, String lastName) {
        String query = "UPDATE account SET last_name = ? WHERE first_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.executeUpdate();
            log.info(String.format("updated an account with %s %s", firstName, lastName));
        } catch (SQLException e) {
            log.severe(e.getMessage());
        }
        return Account.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    /**
     *
     * delete an account in DB
     *
     * @param firstName first name of an account
     */
    public void deleteAccount(String firstName) {
        String query = "DELETE FROM account WHERE first_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, firstName);
            preparedStatement.executeUpdate();
            log.info(String.format("deleted an account with %s", firstName));
        } catch (SQLException e) {
            log.severe(e.getMessage());
        }
    }
}
