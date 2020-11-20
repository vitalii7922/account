package com.project;

import com.project.dao.AccountDAO;
import com.project.db.DatabaseUtilize;
import com.project.domain.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestAccountDAO {

    @Mock
    private DatabaseUtilize databaseUtilize;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private AccountDAO accountDAO;


    @Test
    public void testFindByFirstName() throws SQLException {
        Account account = Account.builder()
                .id(1L)
                .firstName("John")
                .secondName("Bonjovi")
                .build();
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        Assert.assertEquals(account, accountDAO.findByName("John"));
    }
}
