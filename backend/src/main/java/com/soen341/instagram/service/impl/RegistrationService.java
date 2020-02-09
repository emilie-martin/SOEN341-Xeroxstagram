package com.soen341.instagram.service.impl;

import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.exception.account.EmailTakenException;
import com.soen341.instagram.exception.account.InvalidEmailFormatException;
import com.soen341.instagram.exception.account.InvalidUsernameFormatException;
import com.soen341.instagram.exception.account.UsernameTakenException;
import com.soen341.instagram.model.Account;

@Service("registrationService")
public class RegistrationService
{
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void createNewAccount(final Account account)
	{
		checkIfEmailTaken(account.getEmail());

		checkIfUsernameTaken(account.getUsername());

		checkIfEmailFormatValid(account.getEmail());

		checkIfUsernameFormatValid(account.getUsername());

		account.setPassword(passwordEncoder.encode(account.getPassword()));
		account.setCreated(new Date());
		accountRepository.save(account);
	}

	private void checkIfEmailTaken(final String email)
	{
		if (!(accountRepository.findByEmail(email) == null))
		{
			throw new EmailTakenException();
		}
	}

	private void checkIfUsernameTaken(final String username)
	{
		if (!(accountRepository.findByUsername(username) == null))
		{
			throw new UsernameTakenException();
		}
	}

	private void checkIfEmailFormatValid(final String email)
	{
		String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		if (!Pattern.matches(regex, email))
		{
			throw new InvalidEmailFormatException();
		}
	}

	private void checkIfUsernameFormatValid(final String username)
	{
		String regex = "(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]+";
		if (!Pattern.matches(regex, username))
		{
			throw new InvalidUsernameFormatException();
		}

	}
}
