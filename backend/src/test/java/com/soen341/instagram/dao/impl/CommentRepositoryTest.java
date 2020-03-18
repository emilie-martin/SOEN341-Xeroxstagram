package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Comment;
import com.soen341.instagram.model.Picture;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jeasy.random.FieldPredicates.isAnnotatedWith;
import static org.jeasy.random.FieldPredicates.named;

@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    TestEntityManager testEntityManager;

    private Comment comment;

    @Test
    public void findByPicture(){
        EasyRandomParameters parameters = new EasyRandomParameters().excludeField(
                isAnnotatedWith(OneToOne.class).or(isAnnotatedWith(ManyToMany.class)).or(isAnnotatedWith(GeneratedValue.class)));
        EasyRandom easyRandom = new EasyRandom(parameters);

        Account account = easyRandom.nextObject(Account.class);
        account = testEntityManager.persistAndFlush(account);

        Picture picture1 = easyRandom.nextObject(Picture.class);
        picture1.setAccount(account);
        picture1 = testEntityManager.persistAndFlush(picture1);

        Picture picture2 = easyRandom.nextObject(Picture.class);
        picture2.setAccount(account);
        picture2 = testEntityManager.persistAndFlush(picture2);

        comment = easyRandom.nextObject(Comment.class);
        comment.setAccount(account);
        comment.setPicture(picture1);
        commentRepository.save(comment);

        assertThat(commentRepository.findByPicture(picture2)).isEmpty();
        assertThat(commentRepository.findByPicture(comment.getPicture())).hasSize(1);
    }
}
