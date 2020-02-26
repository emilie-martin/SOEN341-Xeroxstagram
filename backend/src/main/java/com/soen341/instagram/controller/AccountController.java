package com.soen341.instagram.controller;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Project
import com.soen341.instagram.dto.account.CreateAccountRequestDTO;
import com.soen341.instgram.service.impl.AccountService;

@RestController
public class AccountController {

  @Autowired
  private AccountService accountService;

  public void updatePassword() {
    // use PUT method to update pw in http request
  }

  public void updateEmail() {
    // use PUT method to update email in http request
  }

  public void updateBirthday() {
    // use PUT method to update bday in http request
  }

  public void getEmail() {
    // use GET method to retrieve email
  }

  public void getBirthday() {
    // use GET method to retrieve bday
  }
}
