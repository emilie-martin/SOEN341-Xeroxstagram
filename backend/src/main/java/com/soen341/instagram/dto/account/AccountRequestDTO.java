package com.soen341.instagram.dto.account;

import java.util.Date;
import java.util.Set;

import com.soen341.instagram.model.Account;

public class AccountRequestDTO
{
	private String email;
	private Date birthday;
	private String password;

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

		public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
