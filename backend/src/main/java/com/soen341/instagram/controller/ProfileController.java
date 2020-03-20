package com.soen341.instagram.controller;

import org.modelmapper.ModelMapper;
// Spring Boot
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Project
import com.soen341.instagram.dto.account.ProfileRequestDTO;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.service.impl.ProfileService;

@RestController
public class ProfileController
{

	@Autowired
	private ProfileService profileService;
	@Autowired
	private ModelMapper modelMapper;

// For now we are not allowing the user to change their username
//	@PutMapping(value = "account/profile/usernameUpdate")
//	public void setUsername(@RequestBody final UpdateProfileRequestDTO profileRequest)
//	{
//		String username = profileRequest.getUsername();
//		profileService.setUsername(username);
//	}

	@PutMapping(value = "account/profile/biographyUpdate")
	public void setBiography(@RequestBody final ProfileRequestDTO profileRequest)
	{
		String biography = profileRequest.getBiography();
		profileService.setBiography(biography);
	}

	@PutMapping(value = "account/profile/emailUpdate")
	public void setEmail(@RequestBody final ProfileRequestDTO profileRequest)
	{
		String email = profileRequest.getEmail();
		profileService.setEmail(email);
	}

	@PutMapping(value = "account/profile/displayNameUpdate")
	public void setDisplayName(@RequestBody final ProfileRequestDTO profileRequest)
	{
		String displayName = profileRequest.getDisplayName();
		profileService.setDisplayName(displayName);
	}

	@PutMapping(value = "account/profile/nameUpdate")
	public void setName(@RequestBody final ProfileRequestDTO profileRequest)
	{
		String firstName = profileRequest.getFirstName();
		String lastName = profileRequest.getLastName();

		profileService.setName(firstName, lastName);
	}

//TODO: Implement changing profile pictures
//	@PostMapping(value = "account/profile/newProfilePicture")
//	public void setProfilePicture(@RequestBody final ProfilePictureDTO picture)
//	{
//		profileService.setProfilePicture(picture);
//	}

	@GetMapping(value = "account/profile/{username}")
	public ProfileRequestDTO getProfile(@PathVariable final String username)
	{
		final Account account = profileService.getProfile(username);
		final ProfileRequestDTO profileRequestDTO = modelMapper.map(account, ProfileRequestDTO.class);
		profileRequestDTO.setNumFollowers(account.getFollowers().size());
		profileRequestDTO.setNumFollowings(account.getFollowing().size());
		profileRequestDTO.setNumPictures(profileService.getNumberOfPictures(username));

		return profileRequestDTO;
	}
}
