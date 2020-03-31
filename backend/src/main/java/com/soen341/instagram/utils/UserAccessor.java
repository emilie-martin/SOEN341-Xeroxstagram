package com.soen341.instagram.utils;

// Spring Boot
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

// Project
import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.model.Account;

public class UserAccessor
{
	public static Account getCurrentAccount(AccountRepository accountRepository)
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
		{
			String username = ((UserDetails) principal).getUsername();
			
			return accountRepository.findByUsername(username);
		}
		else
		{
			throw new IllegalStateException("No user authenticated");
		}
	}
}
