package com.project.service;
import com.project.dao.AccountDAO;
import com.project.domain.Account;
import com.project.exception.ExceptionAccountExists;
import com.project.exception.ExceptionAccountNotFound;
import com.project.exception.ExceptionInvalidInput;
import com.project.response.ResponseMessage;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * test service layer
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountDAO accountDAO;

    @InjectMocks
    private AccountService accountService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void saveAccount() {
        Account account = Account.builder()
                .firstName("John")
                .lastName("Smith")
                .build();

        when(accountDAO.findByName(account.getFirstName())).thenReturn(null);
        when(accountDAO.save(account)).thenReturn(account);
        Assert.assertEquals(account, accountService.saveAccount(account));
    }



    @Test
    public void saveAccountExceptionAccountExists() {
        Account account = Account.builder()
                .firstName("John")
                .lastName("Smith")
                .build();
        when(accountDAO.findByName(account.getFirstName())).thenReturn(account);
        exceptionRule.expect(ExceptionAccountExists.class);
        exceptionRule.expectMessage("Account with name John already exists");
        accountService.saveAccount(account);
    }

    @Test
    public void validateFirstNameNull() {
        exceptionRule.expect(ExceptionInvalidInput.class);
        exceptionRule.expectMessage("First name cannot be null");
        accountService.validateFirstName(null);
    }

    @Test
    public void validateLastNameNull() {
        exceptionRule.expect(ExceptionInvalidInput.class);
        exceptionRule.expectMessage("Last name cannot be null");
        accountService.validateLastName(null);
    }

    @Test
    public void getAccountByName() {
        Account account = Account.builder()
                .firstName("John")
                .lastName("Smith")
                .build();
        when(accountDAO.findByName(account.getFirstName())).thenReturn(account);
        Assert.assertEquals(account, accountService.getAccountByName("John"));
    }

    @Test
    public void getAccountByNameThrowException() {
        when(accountDAO.findByName("John")).thenReturn(null);
        exceptionRule.expect(ExceptionAccountNotFound.class);
        exceptionRule.expectMessage("Account with name John not found");
        accountService.getAccountByName("John");
    }



    @Test
    public void updateAccount() {
        Account beforeUpdateAccount = Account.builder()
                .firstName("John")
                .lastName("Smith")
                .build();

        Account afterUpdateAccount = Account.builder()
                .firstName("John")
                .lastName("Black")
                .build();

        when(accountDAO.findByName("John")).thenReturn(beforeUpdateAccount);
        when(accountDAO.updateLastName("John", "Black")).thenReturn(afterUpdateAccount);
        Assert.assertEquals(afterUpdateAccount, accountService.updateAccount("John", "Black"));
    }

    @Test
    public void deleteAccount() {
        Account account = Account.builder()
                .firstName("John")
                .lastName("Black")
                .build();

        ResponseMessage expectedResponseMessage = new ResponseMessage("Account with first name John has been deleted");

        doNothing().when(accountDAO).deleteAccount("John");
        when(accountDAO.findByName("John")).thenReturn(account);
        Assert.assertEquals(expectedResponseMessage, accountService.deleteAccount("John"));
        verify(accountDAO, times(1)).deleteAccount("John");
    }

    @Test
    public void deleteAccountThrowException() {
        when(accountDAO.findByName("John")).thenReturn(null);
        exceptionRule.expect(ExceptionAccountNotFound.class);
        exceptionRule.expectMessage("Account with name John not found");
        accountService.deleteAccount("John");
    }

    @Test
    public void isJSONValidReturnTrue() {
        String jsonAccount = "{\"firstName\":John\",\"secondName\":\"Black\"}";
        Assert.assertTrue(accountService.isJSONValid(jsonAccount));
    }

    @Test
    public void isJSONValidReturnFalse() {
        String jsonAccount = "{\"firstName\":\",\"secondName\":\"Black\"}";
        Assert.assertFalse(accountService.isJSONValid(jsonAccount));
    }
}
