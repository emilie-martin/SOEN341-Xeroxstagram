package com.soen341.instagram.controller;

import java.util.Date;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Project
import com.soen341.instagram.dto.account.AccountRequestDTO;
import com.soen341.instagram.service.impl.AccountService;

@RestController
public class AccountController {

  @Autowired
  private AccountService accountService;

  @PutMapping(value = "account/passwordModification")
  public void setPassword(@RequestBody final AccountRequestDTO accountRequest)
  {
    accountService.setPassword(accountRequest.getPassword());
  }

  @PutMapping(value = "account/emailModification")
  public void setEmail(@RequestBody final AccountRequestDTO accountRequest)
  {
    String email = accountRequest.getEmail();
    accountService.setEmail(email);
  }

  @PutMapping(value = "account/birthdayModification")
  public void setBirthday(@RequestBody final AccountRequestDTO accountRequest)
  {
    Date birthday = accountRequest.getBirthday();
    accountService.setBirthday(birthday);
  }

  @GetMapping(value = "account/account/account")
  public AccountRequestDTO getAccount() {
    AccountRequestDTO accountRequestDTO = new AccountRequestDTO();

    accountRequestDTO.setEmail(accountService.getEmail());
    accountRequestDTO.setBirthday(accountService.getBirthday());

    return accountRequestDTO;
  }
}
