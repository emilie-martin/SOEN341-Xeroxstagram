package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void beforeEach(){
        accountRepository.deleteAll();

        account = new Account();
        account.setEmail("valid@email.com");
        account.setPassword("password");
        account.setUsername("username");
        account.setFirstName("first");
        account.setLastName("last");
        account.setDisplayName("displayName");
        account.setDateOfBirth(new Date());
        account.setCreated(new Date());
    }

    @Test
    public void findByUsername(){
        accountRepository.save(account);
        assertThat(accountRepository.findByUsername(account.getUsername())).isEqualToComparingFieldByField(account);
    }

    @Test
    public void findByEmail(){
        accountRepository.save(account);
        assertThat(accountRepository.findByEmail(account.getEmail())).isEqualToComparingFieldByField(account);
    }


}
