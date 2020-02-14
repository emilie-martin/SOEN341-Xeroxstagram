package com.soen341.instagram.dto.account;

import lombok.Data;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateAccountRequestDTO
{
	@NotBlank
	private String username;

	@NotBlank
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotNull
	private Date dateOfBirth;
}
