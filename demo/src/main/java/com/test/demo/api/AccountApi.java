package com.test.demo.api;

import com.test.demo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface AccountApi {

    @RequestMapping(value = "/account/getAccountList",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Account>> getAccountList();

    @RequestMapping(value = "/account/createAccount",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<String> createAccount(@RequestBody Account account);

    @RequestMapping(value = "/account/deleteAccount/{accountId}",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    ResponseEntity<String> deleteAccountList(@PathVariable("accountId") Long accountId);

    @RequestMapping(value = "/assetCategory/createAssetCategory",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<String> updateAccount(@RequestBody Account account);

}
