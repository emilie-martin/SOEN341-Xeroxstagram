package com.soen341.instagram.controller;

import java.text.ParseException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.soen341.instagram.dto.AccountDTO;
import com.soen341.instagram.exception.account.BadRequestException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.service.impl.RegistrationService;

@RestController
public class RegistrationController
{

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	RegistrationService registrationService;

	@PostMapping(value = "Account/register")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createAccount(@RequestBody AccountDTO accountDTO)
	{
		Account account = modelMapper.map(accountDTO, Account.class);
		try
		{
			registrationService.createNewAccount(account);
		}
		catch (ParseException e)
		{
			throw new BadRequestException();
		}
	}
}
