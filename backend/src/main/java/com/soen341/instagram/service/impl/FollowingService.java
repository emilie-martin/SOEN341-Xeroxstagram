package com.soen341.instagram.service.impl;

import java.util.Set;

//Spring Boot
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

//Project
import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.exception.account.AccountNotFoundException;
import com.soen341.instagram.exception.account.AlreadyFollowingException;
import com.soen341.instagram.exception.account.SameAccountException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.utils.UserAccessor;

@Service
public class FollowingService
{
	@Autowired
	AccountRepository accountRepository;

	public boolean isFollowing(String username)
	{
		if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))
		{
			return (accountRepository.doesUserFollow(UserAccessor.getCurrentAccount(accountRepository).getUsername(), username)==1);
		}
		else
		{
			return false;
		}
	}

	public void follow(final String username)
	{
		Account following = UserAccessor.getCurrentAccount(accountRepository);
		Account beingFollowed = getAccountToFollow(username);

		startFollowing(following, beingFollowed);
		addFollower(following, beingFollowed);
	}

	public void unfollow(final String username)
	{
		Account following = UserAccessor.getCurrentAccount(accountRepository);
		Account beingFollowed = getAccountToFollow(username);

		stopFollowing(following, beingFollowed);
		removeFollower(following, beingFollowed);
	}

	private void startFollowing(final Account accountFollowing, final Account accountBeingFollowed)
	{
		if (accountFollowing.equals(accountBeingFollowed))
		{
			throw new SameAccountException("Accounts cannot follow themselves");
		}

		Set<Account> followingSet = accountFollowing.getFollowing();

		if (!followingSet.add(accountBeingFollowed))
		{
			throw new AlreadyFollowingException("Account is already following this user");
		}

		accountRepository.save(accountFollowing);
	}

	private void stopFollowing(final Account accountFollowing, final Account accountBeingFollowed)
	{
		if (accountFollowing.equals(accountBeingFollowed))
		{
			throw new SameAccountException("Accounts cannot unfollow themselves");
		}

		Set<Account> followingSet = accountFollowing.getFollowing();

		if (!followingSet.remove(accountBeingFollowed))
		{
			throw new AccountNotFoundException("Cannot unfollow an account that is not already being followed");
		}

		accountRepository.save(accountFollowing);
	}

	private void addFollower(final Account accountFollowing, final Account accountBeingFollowed)
	{
		Set<Account> followerSet = accountBeingFollowed.getFollowers();
		followerSet.add(accountFollowing);

		accountRepository.save(accountBeingFollowed);
	}

	private void removeFollower(final Account accountFollowing, final Account accountBeingFollowed)
	{
		Set<Account> followerSet = accountBeingFollowed.getFollowers();
		followerSet.remove(accountFollowing);

		accountRepository.save(accountBeingFollowed);
	}

	private Account getAccountToFollow(final String username)
	{
		Account account = accountRepository.findByUsername(username);

		if (account == null)
		{
			throw new AccountNotFoundException("Account not found");
		}

		return account;
	}
}
