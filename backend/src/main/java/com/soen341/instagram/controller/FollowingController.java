package com.soen341.instagram.controller;


//Spring Boot
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//Project
import com.soen341.instagram.service.impl.FollowingService;

@RestController
public class FollowingController {

	@Autowired
	FollowingService followingService;
	
	@PostMapping(value = "/account/following/newFollower/{username}")
	public void follow(@PathVariable final String username)
	{
		followingService.follow(username);
	}
	
	@DeleteMapping(value = "/account/following/followerRemoval/{username}")
	public void unfollow(@PathVariable final String username)
	{
		followingService.unfollow(username);
	}
}