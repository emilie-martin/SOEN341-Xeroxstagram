package com.soen341.instagram.service;


import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.exception.account.EmailTakenException;
import com.soen341.instagram.exception.account.InvalidEmailFormatException;
import com.soen341.instagram.exception.account.InvalidUsernameFormatException;
import com.soen341.instagram.exception.account.UsernameTakenException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.service.impl.RegistrationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DataJpaTest
public class RegistrationServiceTest {
    @Autowired
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    private RegistrationService registrationService;

    @Test
    public void createNewAccount(){
        registrationService = new RegistrationService(accountRepository, passwordEncoder);

        //Test invalid email
        Account invalidEmail = new Account();
        invalidEmail.setEmail("badEmail");
        assertThatCode(() -> registrationService.createNewAccount(invalidEmail)).isInstanceOf(InvalidEmailFormatException.class);

        //Test invalid username
        Account invalidUsername = new Account();
        invalidUsername.setEmail("valid@valid.com");
        invalidUsername.setUsername("a");
        assertThatCode(() -> registrationService.createNewAccount(invalidUsername)).isInstanceOf(InvalidUsernameFormatException.class);

        //Create valid account
        Account validAccount = new Account();
        validAccount.setEmail("valid@email.com");
        validAccount.setPassword("password");
        validAccount.setUsername("username");
        validAccount.setFirstName("first");
        validAccount.setLastName("last");
        validAccount.setDateOfBirth(new Date());
        when(passwordEncoder.encode(anyString())).then(returnsFirstArg());
        registrationService.createNewAccount(validAccount);
        assertThat(accountRepository.findByEmail(validAccount.getEmail())).isEqualTo(validAccount);

        //Test Email taken
        Account takenEmail = new Account();
        takenEmail.setEmail(validAccount.getEmail());
        assertThatCode(() -> registrationService.createNewAccount(takenEmail)).isInstanceOf(EmailTakenException.class);

        //Test Username taken
        Account usernameTaken = new Account();
        usernameTaken.setUsername(validAccount.getUsername());
        assertThatCode(() -> registrationService.createNewAccount(usernameTaken)).isInstanceOf(UsernameTakenException.class);
    }
}
