package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, String> {
    Account findByUsername(String username);
    Account findByEmail(String email);
}
