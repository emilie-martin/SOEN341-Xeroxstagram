package com.soen341.instagram.service;

import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.Set;

// Testing
import static org.assertj.core.api.Assertions.assertThat;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import static org.jeasy.random.FieldPredicates.isAnnotatedWith;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

// Project
import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.exception.account.AccountNotFoundException;
import com.soen341.instagram.exception.account.AlreadyFollowingException;
import com.soen341.instagram.exception.account.SameAccountException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.service.impl.FollowingService;
import com.soen341.instagram.utils.UserAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserAccessor.class)
public class FollowingServiceTest
{
	@InjectMocks
	private FollowingService followingService;
	@Mock
	private AccountRepository accountRepository;
	private Account follower;
	private Account beingFollowed;
	@Before
	public void beforeEach()
	{
		EasyRandomParameters parameters = new EasyRandomParameters()
			.excludeField(isAnnotatedWith(OneToOne.class)
			.or(isAnnotatedWith(ManyToMany.class)));
		EasyRandom easyRandom = new EasyRandom(parameters);
		follower = easyRandom.nextObject(Account.class);
		beingFollowed = easyRandom.nextObject(Account.class);

		PowerMockito.mockStatic(UserAccessor.class);
		PowerMockito.when(UserAccessor.getCurrentAccount(accountRepository)).thenReturn(follower);

		when(accountRepository.findByUsername(beingFollowed.getUsername())).thenReturn(beingFollowed);
	}

	@Test
	public void isFollowingAnotherAccount_ExpectTrue()
	{
		when(accountRepository.doesUserFollow(follower.getUsername(), beingFollowed.getUsername())).thenReturn(1);

		assertThat(followingService.isFollowing(beingFollowed.getUsername())).isTrue();
	}

	@Test
	public void isNotFollowingAnotherAccount_ExpectFalse()
	{
		when(accountRepository.doesUserFollow(follower.getUsername(), beingFollowed.getUsername())).thenReturn(0);

		assertThat(followingService.isFollowing(beingFollowed.getUsername())).isFalse();
	}

	@Test
	public void followNewAccountSuccessfully()
	{
		followingService.follow(beingFollowed.getUsername());

		assertThat(follower.getFollowing().contains(beingFollowed)).isTrue();
		assertThat(beingFollowed.getFollowers().contains(follower)).isTrue();
	}

	@Test(expected = SameAccountException.class)
	public void followSameAccount_ExpectSameAccountException()
	{
		when(accountRepository.findByUsername(follower.getUsername())).thenReturn(follower);

		followingService.follow(follower.getUsername());
	}

	@Test(expected = AlreadyFollowingException.class)
	public void followAlreadyFollowingAccount_ExpectAlreadyFollowingException()
	{
		followingService.follow(beingFollowed.getUsername());
		followingService.follow(beingFollowed.getUsername());
	}

	@Test
	public void unfollowAccountSuccessfully()
	{
		// Start following
		Set<Account> followingSet = follower.getFollowing();
		followingSet.add(beingFollowed);

		// Add follower
		Set<Account> followerSet = beingFollowed.getFollowers();
		followerSet.add(follower);

		followingService.unfollow(beingFollowed.getUsername());

		assertThat(follower.getFollowing().contains(beingFollowed)).isFalse();
		assertThat(beingFollowed.getFollowers().contains(follower)).isFalse();
	}

	@Test(expected = SameAccountException.class)
	public void unfollowSameAccount_ExpectSameAccountException()
	{
		when(accountRepository.findByUsername(follower.getUsername())).thenReturn(follower);

		followingService.unfollow(follower.getUsername());
	}

	@Test(expected = AccountNotFoundException.class)
	public void unfollowNotFollowingAccount_ExpectAccountNotFoundException()
	{
		followingService.unfollow(beingFollowed.getUsername());
	}
}
