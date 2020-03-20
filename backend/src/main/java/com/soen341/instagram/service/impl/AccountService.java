package com.soen341.instagram.service.impl;

import java.util.Date;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Project
import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.utils.AccountVerifier;
import com.soen341.instagram.utils.UserAccessor;

@Service
public class AccountService
{
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void setPassword(final String newPassword)
	{

		final Account account = UserAccessor.getCurrentAccount(accountRepository);
		String oldPassword = account.getPassword();

		AccountVerifier.checkIfSamePassword(newPassword, oldPassword, passwordEncoder);

		account.setPassword(passwordEncoder.encode(newPassword));
		accountRepository.save(account);
	}

	public String getEmail()
	{
		return UserAccessor.getCurrentAccount(accountRepository).getEmail();
	}

	public void setEmail(final String newEmail)
	{
		AccountVerifier.checkIfEmailTaken(newEmail, accountRepository);

		final Account account = UserAccessor.getCurrentAccount(accountRepository);
		account.setEmail(newEmail);
		accountRepository.save(account);
	}

	public Date getBirthday()
	{
		return UserAccessor.getCurrentAccount(accountRepository).getDateOfBirth();
	}

	public void setBirthday(final Date bday)
	{
		final Account account = UserAccessor.getCurrentAccount(accountRepository);
		account.setDateOfBirth(bday);
		accountRepository.save(account);
	}
}
