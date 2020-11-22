package com.project;
import com.project.dao.AccountDAO;
import com.project.domain.Account;
import com.project.service.AccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnitTest {

    @Mock
    AccountDAO accountDAO;

    @InjectMocks
    AccountService accountService;

    @Test
    public void testGetByFirstName() {
        Account account = Account.builder()
                .firstName("Dmitrii")
                .lastName("Dmitriev")
                .build();
        when(accountDAO.findByName("Dmitrii")).thenReturn(account);
        Assert.assertEquals(account, accountService.getAccountByName("Dmitrii"));
    }
}
