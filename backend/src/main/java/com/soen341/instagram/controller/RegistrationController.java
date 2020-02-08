package com.soen341.instagram.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.soen341.instagram.dto.account.CreateAccountRequestDTO;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.service.impl.RegistrationService;

@RestController
public class RegistrationController
{

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RegistrationService registrationService;

	@PostMapping(value = "account/register")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createAccount(@RequestBody @Valid CreateAccountRequestDTO createAccountRequestDTO)
	{
		Account account = modelMapper.map(createAccountRequestDTO, Account.class);
		registrationService.createNewAccount(account);
	}
}
