package com.soen341.instagram.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soen341.instagram.dao.impl.AccountRepository;

@Service("UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService
{
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
	{
		return new UserDetailsImpl(accountRepository.findByUsername(username));
	}

}
