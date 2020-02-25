package com.soen341.instagram.controller;

// Spring Boot
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Project
import com.soen341.instagram.dto.account.ProfileRequestDTO;
import com.soen341.instagram.service.impl.ProfileService;

@RestController
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
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

	@GetMapping(value = "account/profile/profile")
	public ProfileRequestDTO getProfile()
	{
		ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO();
		
		// Pictures
		profileRequestDTO.setNumPictures(profileService.getNumberOfPictures());
		
		// Followers
		profileRequestDTO.setNumFollowers(profileService.getNumberOfFollowers());
		profileRequestDTO.setFollowers(profileService.getFollowers());
		
		// Display Name
		profileRequestDTO.setDisplayName(profileService.getDisplayName());
		
		// Full Name
		profileRequestDTO.setFirstName(profileService.getFirstName());
		profileRequestDTO.setLastName(profileService.getLastName());
		
		//Biography
		profileRequestDTO.setBiography(profileService.getBiography());
		
		// Email
		profileRequestDTO.setEmail(profileService.getEmail());
		
		return profileRequestDTO;
	}
}
