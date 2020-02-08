package com.soen341.instagram.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.exception.account.AccountExistException;
import com.soen341.instagram.exception.account.InvalidEmailException;
import com.soen341.instagram.model.Account;

@Service("registrationService")
public class RegistrationService
{
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void createNewAccount(final Account account) throws ParseException
	{
		if (accountRepository.findByUsername(account.getUsername()) == null
				&& accountRepository.findByEmail(account.getEmail()) == null)
		{
			if (!isEmailFormatValid(account.getEmail()))
			{
				throw new InvalidEmailException();
			}

			account.setPassword(passwordEncoder.encode(account.getPassword()));
			account.setCreated(getCurrentDate());
			accountRepository.save(account);
		}
		else
		{
			throw new AccountExistException("Username already exists");
		}
	}

	protected Date getCurrentDate() throws ParseException
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		return new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now));

	}

	protected boolean isEmailFormatValid(final String email)
	{
		String regex = "\\w+\\@\\w+\\.[A-Za-z]+";
		return Pattern.matches(regex, email);
	}

}
