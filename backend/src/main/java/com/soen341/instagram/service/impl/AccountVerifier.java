package com.soen341.instagram.service.impl;

import com.soen341.instagram.dao.impl.*;
import com.soen341.instagram.exception.account.EmailTakenException;
import com.soen341.instagram.exception.account.InvalidEmailFormatException;
import com.soen341.instagram.exception.account.InvalidNameException;
import com.soen341.instagram.exception.account.InvalidUsernameFormatException;
import com.soen341.instagram.exception.account.UsernameTakenException;
import java.util.regex.Pattern;

public class AccountVerifier {
	
	public static void checkIfEmailTaken(final String email, AccountRepository accountRepository)
	{
		if (!(accountRepository.findByEmail(email) == null))
		{
			throw new EmailTakenException();
		}
	}

	public static void checkIfUsernameTaken(final String username, AccountRepository accountRepository)
	{
		if (!(accountRepository.findByUsername(username) == null))
		{
			throw new UsernameTakenException();
		}
	}

	public static void checkIfEmailFormatValid(final String email)
	{
		String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		if (!Pattern.matches(regex, email))
		{
			throw new InvalidEmailFormatException();
		}
	}
	
	public static void checkIfUsernameFormatValid(final String username)
	{
		String regex = "(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]+";
		if (!Pattern.matches(regex, username) || username.length() < 3 || username.length() > 30)
		{
			throw new InvalidUsernameFormatException();
		}	
	}
	
	public static void checkNameFormat(final String name)
	{
		String regex = "^$|\\s+"; // empty or white spaces
		if(Pattern.matches(regex, name))
		{
			throw new InvalidNameException();
		}
	}
	



}
