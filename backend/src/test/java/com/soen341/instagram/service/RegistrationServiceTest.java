package com.soen341.instagram.service;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.exception.account.EmailTakenException;
import com.soen341.instagram.exception.account.InvalidEmailFormatException;
import com.soen341.instagram.exception.account.InvalidUsernameFormatException;
import com.soen341.instagram.exception.account.UsernameTakenException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.service.impl.RegistrationService;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest
{

	@InjectMocks
	private RegistrationService registrationService;
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private PasswordEncoder passwordEncoder;

	private Account validAccountRegistered;

	@Before
	public void setup()
	{
		validAccountRegistered = new Account();
		validAccountRegistered.setEmail("valid@email.com");
		validAccountRegistered.setPassword("password");
		validAccountRegistered.setUsername("username");
		validAccountRegistered.setFirstName("first");
		validAccountRegistered.setLastName("last");
		validAccountRegistered.setDisplayName("displayName");
		validAccountRegistered.setDateOfBirth(new Date());

		// Mocking the existence of an account in database
		Mockito.when(accountRepository.findByEmail("valid@email.com")).thenReturn(validAccountRegistered);
		Mockito.when(accountRepository.findByUsername("username")).thenReturn(validAccountRegistered);
	}

	@Test(expected = InvalidEmailFormatException.class)
	public void createNewAccount_withInvalidEmail_ExpectInvalidEmailFormatException()
	{
		final Account account = new Account();
		account.setEmail("invalidEmail");
		registrationService.createNewAccount(account);
	}

	@Test(expected = InvalidUsernameFormatException.class)
	public void createNewAccount_withInvalidUsername_ExpectInvalidUsernameFormatException()
	{
		final Account account = new Account();
		account.setEmail("valid@valid.com");
		account.setUsername("a");
		registrationService.createNewAccount(account);
	}

	@Test(expected = EmailTakenException.class)
	public void createNewAccount_withEmailTaken_ExpectEmailTakenException()
	{
		final Account account = new Account();
		account.setEmail(validAccountRegistered.getEmail());
		registrationService.createNewAccount(account);
	}

	@Test(expected = UsernameTakenException.class)
	public void createNewAccount_withUsernameTaken_ExpectUsernameTakenException()
	{
		final Account account = new Account();
		account.setUsername(validAccountRegistered.getUsername());
		registrationService.createNewAccount(account);
	}

	@Test
	public void createNewAccountSuccessfully()
	{
		final Account account = new Account();
		account.setEmail("johndoe@valid.com");
		account.setUsername("JohnDoe");
		account.setPassword("password");
		registrationService.createNewAccount(account);
	}
}
