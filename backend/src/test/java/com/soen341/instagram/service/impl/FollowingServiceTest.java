package com.soen341.instagram.service.impl;


import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.exception.account.AlreadyFollowingException;
import com.soen341.instagram.exception.account.InvalidUsernameFormatException;
import com.soen341.instagram.exception.account.SameAccountException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.security.UserDetailsImpl;
import com.soen341.instagram.service.impl.FollowingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FollowingServiceTest {
   // @Autowired
   // private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    SecurityContext securityContext = Mockito.mock(SecurityContextImpl.class);

    Authentication authentication = Mockito.mock(Authentication.class);

    FollowingService followingService;

    Account follower;
    Account beingFollowed;

    @BeforeEach
    public void beforeEach(){
        followingService = new FollowingService();

        /*EasyRandomParameters parameters = new EasyRandomParameters().excludeField(
                isAnnotatedWith(OneToOne.class).or(isAnnotatedWith(ManyToMany.class)));
        EasyRandom easyRandom = new EasyRandom(parameters);*/

        //follower = easyRandom.nextObject(Account.class);
        follower = accountRepository.save(follower);

        //beingFollowed = easyRandom.nextObject(Account.class);
        beingFollowed = accountRepository.save(beingFollowed);

        SecurityContextHolder.setContext(securityContext);
        UserDetailsImpl principal = new UserDetailsImpl(follower);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
    }

    @Test
    @Transactional
    public void isFollowing()
    {
        //start following
        Set<Account> followingSet = follower.getFollowing();
        followingSet.add(beingFollowed);
        accountRepository.save(follower);

        //add follower
        Set<Account> followerSet = beingFollowed.getFollowers();
        followerSet.add(follower);
        beingFollowed = accountRepository.save(beingFollowed);

        //Test when the user following the other account
        assertThat(followingService.isFollowing(beingFollowed.getUsername())).isTrue();

        //Test if the user following itself
        assertThat(followingService.isFollowing(follower.getUsername())).isFalse();

        //Test when the user not following the other account
        assertThat(followingService.isFollowing(follower.getUsername())).isFalse();
    }


    @Test
    @Transactional
    public void follow(){

        //Test following a new account
        assertThat(accountRepository.doesUserFollow(follower.getUsername(), beingFollowed.getUsername())).isEqualTo(0);
        followingService.follow(beingFollowed.getUsername());
        assertThat(accountRepository.doesUserFollow(follower.getUsername(), beingFollowed.getUsername())).isEqualTo(1);

        //Test following same account
        assertThatCode(() -> followingService.follow(follower.getUsername())).isInstanceOf(SameAccountException.class);

        //Test following an already following account
        assertThatCode(() -> followingService.follow(beingFollowed.getUsername())).isInstanceOf(AlreadyFollowingException.class);
    }

    @Test
    @Transactional
    public void unfollowAlreadyFollowingAccount(){
        //start following
        Set<Account> followingSet = follower.getFollowing();
        followingSet.add(beingFollowed);
        accountRepository.save(follower);

        //add follower
        Set<Account> followerSet = beingFollowed.getFollowers();
        followerSet.add(follower);
        beingFollowed = accountRepository.save(beingFollowed);

        followingService.unfollow(beingFollowed.getUsername());
        assertThat(accountRepository.doesUserFollow(follower.getUsername(), beingFollowed.getUsername())).isEqualTo(0);
    }

    @Test
    @Transactional
    public void unfollowSameAccount(){
        //start following
        Set<Account> followingSet = follower.getFollowing();
        followingSet.add(beingFollowed);
        accountRepository.save(follower);

        //add follower
        Set<Account> followerSet = beingFollowed.getFollowers();
        followerSet.add(follower);
        beingFollowed = accountRepository.save(beingFollowed);

        followingService.unfollow(beingFollowed.getUsername());
        assertThat(accountRepository.doesUserFollow(follower.getUsername(), beingFollowed.getUsername())).isEqualTo(0);
    }


    
}
