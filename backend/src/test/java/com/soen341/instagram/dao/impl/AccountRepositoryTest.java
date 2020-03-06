package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Picture;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jeasy.random.FieldPredicates.*;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void beforeEach(){
        EasyRandomParameters parameters = new EasyRandomParameters().excludeField(
                isAnnotatedWith(OneToOne.class).or(isAnnotatedWith(ManyToMany.class)));
        EasyRandom easyRandom = new EasyRandom(parameters);

        account = easyRandom.nextObject(Account.class);
        account.setEmail("valid@email.com");
        account.setUsername("username");

        accountRepository.save(account);
    }

    @Test
    public void findByUsername(){
        assertThat(accountRepository.findByUsername("doesntExist")).isNull();
        assertThat(accountRepository.findByUsername(account.getUsername())).isEqualToComparingFieldByField(account);
    }

    @Test
    public void findByEmail(){
        assertThat(accountRepository.findByEmail("doesntExist")).isNull();
        assertThat(accountRepository.findByEmail(account.getEmail())).isEqualToComparingFieldByField(account);
    }


}
