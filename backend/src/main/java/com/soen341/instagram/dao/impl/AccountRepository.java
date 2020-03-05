package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends PagingAndSortingRepository<Account, String> {
    Account findByUsername(String username);
    Account findByEmail(String email);
    
    @Query(value =  "SELECT COUNT(follow)>0 FROM account_following AS follow "
    		+ "WHERE follow.account_username = :account AND follow.following_username = :account2 "
    		+ "LIMIT 1;", nativeQuery = true)
    boolean doesUserFollow(@Param("account") String account, @Param("account2") String account2);
    
    
}
