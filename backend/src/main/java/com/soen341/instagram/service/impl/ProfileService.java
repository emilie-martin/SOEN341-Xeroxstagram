package com.soen341.instagram.service.impl;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Project
import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.utils.AccountVerifier;
import com.soen341.instagram.utils.UserAccessor;

@Service
public class ProfileService
{

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	PictureRepository pictureRepository;

// Disallowing changing the username for now
//	public void setUsername(final String username)
//	{
//		AccountVerifier.checkIfUsernameTaken(username, accountRepository);
//		AccountVerifier.checkIfUsernameFormatValid(username);
//		
//		final Account account = getCurrentAccount();
//		account.setUsername(username);
//		accountRepository.save(account);
//	}

//TODO: Implement changing profile pictures	
//	public void setProfilePicture(ProfilePictureDTO profilePicture)
//	{
//		final Account account = getCurrentAccount();
//		//account.setProfilePicture(profilePicture);
//		accountRepository.save(account);
//	}

	public void setEmail(final String email)
	{
		AccountVerifier.checkIfEmailFormatValid(email);
		AccountVerifier.checkIfEmailTaken(email, accountRepository);

		final Account account = getCurrentAccount();
		account.setEmail(email);
		accountRepository.save(account);
	}

	public void setBiography(final String biography)
	{
		final Account account = getCurrentAccount();
		account.setBiography(biography);
		accountRepository.save(account);
	}

	public void setDisplayName(final String displayName)
	{
		final Account account = getCurrentAccount();
		account.setDisplayName(displayName);
		accountRepository.save(account);
	}

	public void setName(final String firstName, final String lastName)
	{
		final Account account = getCurrentAccount();

		if (firstName != null)
		{
			AccountVerifier.checkNameFormat(firstName);
			account.setFirstName(firstName);
		}

		if (lastName != null)
		{
			AccountVerifier.checkNameFormat(lastName);
			account.setLastName(lastName);
		}

		accountRepository.save(account);
	}

	public int getNumberOfPictures(final String username)
	{
		final Account account = accountRepository.findByUsername(username);
		return pictureRepository.findByAccount(account).size();
	}

	public Account getProfile(String username)
	{
		return accountRepository.findByUsername(username);
	}

	// Helper methods
	private Account getCurrentAccount()
	{
		return UserAccessor.getCurrentAccount(accountRepository);
	}
}
