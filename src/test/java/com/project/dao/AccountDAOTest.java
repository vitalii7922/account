package com.project.dao;

import com.project.db.DatabaseSource;
import com.project.domain.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * test dao layer
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountDAOTest {

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    java.sql.Connection connection;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private AccountDAO accountDAO;


    @Test
    public void testDatabaseConnection() {
        Assert.assertNotNull(DatabaseSource.getConnection());
    }

    @Test
    public void testSave() throws SQLException {
        Account account = Account.builder()
                .firstName("John")
                .lastName("Wall")
                .build();

        when(connection.prepareStatement(eq("INSERT INTO account(first_name, last_name) VALUES(?,?)")))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        Assert.assertEquals(account, accountDAO.save(account));

        verify(preparedStatement).setString(eq(1), eq("John"));
        verify(preparedStatement).setString(eq(2), eq("Wall"));
    }

    @Test
    public void testSaveThrowNullPointerException() throws SQLException {
        when(connection.prepareStatement(eq("INSERT INTO account(first_name, last_name) VALUES(?,?)")))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        Account account = Account.builder()
                .firstName("John")
                .lastName("Wall")
                .build();
        Assert.assertNull(accountDAO.save(account));
    }

    @Test
    public void testFindByNameReturnAccount() throws SQLException {
        String firstName = "John";
        Account account = Account.builder()
                .firstName("John")
                .lastName("Wall")
                .build();

        when(connection.prepareStatement(eq("SELECT first_name, last_name FROM account WHERE first_name = ?")))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString(eq("first_name"))).thenReturn(firstName);
        when(resultSet.getString(eq("last_name"))).thenReturn(account.getLastName());

        Assert.assertEquals(account, accountDAO.findByName(firstName));

        verify(preparedStatement).setString(eq(1), eq(firstName));
    }


    @Test
    public void testFindByNameReturnNull() throws SQLException {
        String firstName = "John";

        when(connection.prepareStatement(eq("SELECT first_name, last_name FROM account WHERE first_name = ?")))
                .thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Assert.assertNull(accountDAO.findByName(firstName));
    }

    @Test
    public void testUpdateLastName() throws SQLException {
        Account accountExpected = Account
                .builder()
                .firstName("John")
                .lastName("Black")
                .build();

        when(connection.prepareStatement(eq("UPDATE account SET last_name = ? WHERE first_name = ?")))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Assert.assertEquals(accountExpected, accountDAO.updateLastName(accountExpected.getFirstName(),
                accountExpected.getLastName()));

        verify(preparedStatement).setString(eq(1), eq(accountExpected.getLastName()));
        verify(preparedStatement).setString(eq(2), eq(accountExpected.getFirstName()));
    }


    @Test
    public void testDeleteAccount() throws SQLException {
        String firstName = "John";

        when(connection.prepareStatement(eq("DELETE FROM account WHERE first_name = ?")))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        accountDAO.deleteAccount(firstName);
        verify(preparedStatement).setString(eq(1), eq(firstName));
    }

}
