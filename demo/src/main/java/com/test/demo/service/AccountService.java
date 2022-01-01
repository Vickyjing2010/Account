package com.test.demo.service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.test.demo.dao.AccountDao;
import com.test.demo.dao.AccountEntity;
import com.test.demo.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountDao accountDao;

    @Transactional(rollbackOn=Exception.class)
    public void createAccount(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(account, accountEntity);
        AccountEntity account1 = accountDao.createAccount(accountEntity);
        log.info("account created: {}", account1);
    }

    @Transactional
    public void deleteAccount(Long id) {
        accountDao.deleteAccount(id);
    }

    @Transactional
    public void updateAccount(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(account, accountEntity);
        AccountEntity account1 = accountDao.createAccount(accountEntity);
        log.info("account created: {}", account1);
    }

    public List<Account> getAccountList() {
        List<Account> accountList = new ArrayList<>();
        List<AccountEntity> entityList = accountDao.findAllAccountList();
        entityList.forEach(e-> {
            Account account = new Account();
            BeanUtils.copyProperties(e, account);
            accountList.add(account);
        });
        return accountList;
    }


}
