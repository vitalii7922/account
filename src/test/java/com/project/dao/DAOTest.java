/*
package com.project.dao;

import com.project.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DAOTest {

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    Connection connection;


    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private AccountDAO accountDAO;


    @Test
    public void testSave() throws SQLException {
        when(connection
                .prepareStatement("INSERT INTO account(first_name, last_name) VALUES(?,?)"))
                .thenReturn(preparedStatement);

        doNothing().when(preparedStatement).setString(1, "John");
        Account account = Account.builder()
                .firstName("John")
                .lastName("Wall")
                .build();
        accountDAO.save(account);


    }

}
*/
