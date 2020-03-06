package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Picture;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jeasy.random.FieldPredicates.isAnnotatedWith;

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
        EasyRandomParameters parameters = new EasyRandomParameters().excludeField(
                isAnnotatedWith(OneToOne.class).or(isAnnotatedWith(ManyToMany.class)));
        EasyRandom easyRandom = new EasyRandom(parameters);

        Account account1 = easyRandom.nextObject(Account.class);
        account1 = testEntityManager.persistAndFlush(account1);

        Account account2 = easyRandom.nextObject(Account.class);
        account2 = testEntityManager.persistAndFlush(account2);

        picture = new Picture();
        picture.setAccount(account1);
        picture.setFilePath("");
        picture.setCreated(new Date());
        pictureRepository.save(picture);

        assertThat(pictureRepository.findByAccount(account2)).isEmpty();
        assertThat(pictureRepository.findByAccount(picture.getAccount())).hasSize(1);
    }
}
