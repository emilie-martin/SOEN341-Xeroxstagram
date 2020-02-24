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

	@GetMapping(value = "account/profile/pictureCount")
	public ProfileRequestDTO getNumberOfPictures()
	{
		ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO();
		profileRequestDTO.setNumPictures(profileService.getNumberOfPictures());
		
		return profileRequestDTO;
	}
	
	
	@GetMapping(value = "account/profile/followerCount")
	public ProfileRequestDTO getNumberOfFollowers()
	{
		ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO();
		profileRequestDTO.setNumFollowers(profileService.getNumberOfFollowers());
		
		return profileRequestDTO;
	}
	
	@GetMapping(value = "account/profile/followers")
	public ProfileRequestDTO getFollowers()
	{
		ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO();
		profileRequestDTO.setFollowers(profileService.getFollowers());
		
		return profileRequestDTO;
	}
	
	@GetMapping(value = "account/profile/displayName")
	public ProfileRequestDTO getDisplayName()
	{
		ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO();
		profileRequestDTO.setFirstName(profileService.getFirstName());
		
		return profileRequestDTO;
	}
	
	@GetMapping(value = "account/profile/biography")
	public ProfileRequestDTO getBiography()
	{
		ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO();
		profileRequestDTO.setBiography(profileService.getBiography());
		
		return profileRequestDTO;
	}
	
	@GetMapping(value = "account/profile/firstName")
	public ProfileRequestDTO getFirstName()
	{
		ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO();
		profileRequestDTO.setFirstName(profileService.getFirstName());
		
		return profileRequestDTO;
	}
	
	@GetMapping(value = "account/profile/lastName")
	public ProfileRequestDTO getLastName()
	{
		ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO();
		profileRequestDTO.setLastName(profileService.getLastName());
		
		return profileRequestDTO;
	}
}
