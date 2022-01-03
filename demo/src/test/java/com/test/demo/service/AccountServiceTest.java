package com.test.demo.service;

import com.test.demo.dao.AccountDao;
import com.test.demo.dao.AccountEntity;
import com.test.demo.model.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AccountService.class})
public class AccountServiceTest {

    @Autowired
    @InjectMocks
    private AccountService accountService;

    @MockBean
    private AccountDao accountDao;

    @Test
    public void testCreateAccount() {
        AccountEntity mockAccount = mockAccountEntity();
        Mockito.when(accountDao.createAccount(ArgumentMatchers.any())).thenReturn(mockAccount);
        String result = accountService.createAccount(mockAccount());
        Assert.assertNotNull(result);
    }

    @Test
    public void testUpdateAccount() {
    }

    @Test
    public void testGetAccount() {
    }

    @Test
    public void testDeleteAccount() {
    }

    private AccountEntity mockAccountEntity() {
        AccountEntity account = new AccountEntity();
        account.setName("accountNameTest");
        account.setGender("male");
        account.setPassword("23445");
        return account;
    }

    private Account mockAccount() {
        Account account = new Account();
        account.setName("accountNameTest");
        account.setGender("male");
        account.setPassword("23445");
        return account;
    }


}
