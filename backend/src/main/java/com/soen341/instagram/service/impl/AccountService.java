package com.soen341.instagram.service.impl;

import java.util.Date;
import java.util.regex.Pattern;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import com.soen341.instagram.exception.account.InvalidEmailFormatException;
import com.soen341.instagram.security.userDetail;
import com.soen341.instagram.security.userDetailsService;

// Project
import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.model.Account;

@Service
public class AccountService 
{
	@Autowired
	AccountRepository accountRepository;

  private void updatePassword(final String newPassword) {
    if(!getCurrentAccount().getPassword().equals(newPassword)) {
      throw ("The new password cannot be the same as the old password");
    }
    else {
      final Account account = getCurrentAccount();
      account.setPassword(newPassword);
      accountRepository.save(account);
    }
  }

  private String getEmail() {
    return getCurrentAccount().getEmail();
  }

  private void updateEmail(final String newEmail) {
		String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

		if (!Pattern.matches(regex, email))
		{
			throw new InvalidEmailFormatException();
		}
    else {
      final Account account = getCurrentAccount();
      account.setEmail(newEmail);
      accountRepository.save(account);
    }
  }

  private Date getBirthday() {
    return getCurrentAccount().getBirthday();
  }

  private void updateBirthday(final Date bday) {
    final Account account = getCurrentAccount();
    account.setDateOfBirth(bday);
    accountRepository.save(account);
  }

  // Helper method
  public Account getCurrentAccount() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    String username = ((UserDetails)authentication.getPrincipal()).getUsername();

    return accountRepository.findByUsername(username);
  }
}
