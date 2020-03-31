package com.soen341.instagram.dto.account;

import java.util.Set;

public class ProfileRequestDTO
{

	private String username;
	private String email;
	private String displayName;
	private String biography;
	private String firstName;
	private String lastName;
	private int numFollowers;
	private int numFollowings;
	private int numPictures;
	private Set<AccountDTO> followers;
	private Set<AccountDTO> followings;

	public int getNumPictures()
	{
		return numPictures;
	}

	public void setNumPictures(int numPictures)
	{
		this.numPictures = numPictures;
	}

	public int getNumFollowers()
	{
		return numFollowers;
	}

	public void setNumFollowers(int numFollowers)
	{
		this.numFollowers = numFollowers;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getBiography()
	{
		return biography;
	}

	public void setBiography(String biography)
	{
		this.biography = biography;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public int getNumFollowings()
	{
		return numFollowings;
	}

	public void setNumFollowings(int numFollowings)
	{
		this.numFollowings = numFollowings;
	}

	public Set<AccountDTO> getFollowers()
	{
		return followers;
	}

	public void setFollowers(Set<AccountDTO> followers)
	{
		this.followers = followers;
	}

	public Set<AccountDTO> getFollowings()
	{
		return followings;
	}

	public void setFollowings(Set<AccountDTO> followings)
	{
		this.followings = followings;
	}

}
