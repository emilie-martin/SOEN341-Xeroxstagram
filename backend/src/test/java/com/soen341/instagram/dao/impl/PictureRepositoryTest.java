package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Picture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PictureRepositoryTest {
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    TestEntityManager testEntityManager;

    private Picture picture;

    @BeforeEach
    public void beforeEach(){
        pictureRepository.deleteAll();
    }

    @Test
    public void findByAccount(){
        Account account = new Account();
        account.setEmail("valid@email.com");
        account.setPassword("password");
        account.setUsername("username");
        account.setFirstName("first");
        account.setLastName("last");
        account.setDateOfBirth(new Date());
        account.setCreated(new Date());
        account = testEntityManager.persistAndFlush(account);

        picture = new Picture();
        picture.setAccount(account);
        picture.setFilePath("");
        picture.setCreated(new Date());
        pictureRepository.save(picture);

        assertThat(pictureRepository.findByAccount(picture.getAccount())).hasSize(1);
    }
}
