package com.soen341.instagram.security;

import java.util.Collection;

// Spring Boot
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Project
import com.soen341.instagram.model.Account;

public class UserDetailsImpl implements UserDetails
{
	private String username;
	private String password;
	Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Account account)
	{
		this.username = account.getUsername();
		this.password = account.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return null;
	}

	@Override
	public String getPassword()
	{
		return this.password;
	}

	@Override
	public String getUsername()
	{
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
}
