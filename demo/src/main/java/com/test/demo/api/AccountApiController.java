package com.test.demo.api;

import com.test.demo.model.Account;
import com.test.demo.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class AccountApiController implements AccountApi{
    private static final Logger log = LoggerFactory.getLogger(AccountApiController.class);
    @Autowired
    private AccountService accountService;

    public ResponseEntity<String> createAccount(Account account) {
        try {
            accountService.createAccount(account);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Account>> getAccountList() {
        try {
            return new ResponseEntity<>(accountService.getAccountList(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteAccountList(Long id) {
        try {
            accountService.deleteAccount(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<String> updateAccount(@RequestBody Account account){
        try {
            accountService.updateAccount(account);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
