package com.test.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AccountDao extends JpaRepository<AccountEntity,Long> {
    default AccountEntity createAccount(AccountEntity account) {
        return save(account);
    }

    default void deleteAccount(Long id) {
        deleteById(id);
    }

    @Query(nativeQuery = true, value="select * from account")
    List<AccountEntity> findAllAccountList();

}
