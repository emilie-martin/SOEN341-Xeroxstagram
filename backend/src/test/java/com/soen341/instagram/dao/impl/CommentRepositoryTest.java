package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Comment;
import com.soen341.instagram.model.Picture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    TestEntityManager testEntityManager;

    private Comment comment;

    @BeforeEach
    public void beforeEach(){
        commentRepository.deleteAll();
    }

    @Test
    public void findByPicture(){
        Account account = new Account();
        account.setEmail("valid@email.com");
        account.setPassword("password");
        account.setUsername("username");
        account.setFirstName("first");
        account.setLastName("last");
        account.setDateOfBirth(new Date());
        account.setCreated(new Date());
        account = testEntityManager.persistAndFlush(account);

        Picture picture = new Picture();
        picture.setCreated(new Date());
        picture.setFilePath("");
        picture.setAccount(account);
        picture = testEntityManager.persistAndFlush(picture);

        comment = new Comment();
        comment.setComment("toto");
        comment.setCreated(new Date());
        comment.setAccount(account);
        comment.setPicture(picture);
        commentRepository.save(comment);

        assertThat(commentRepository.findByPicture(comment.getPicture())).hasSize(1);
    }
}
