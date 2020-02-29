package com.soen341.instagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soen341.instagram.service.impl.LikeService;

@RestController
public class LikeController {
	@Autowired
	private LikeService likeService;
	
	@PostMapping(value = "/comment/like/{commentId}")
	public int likeComment(@PathVariable final long commentId)
	{
		return likeService.likeComment(commentId);
	}

	@PostMapping(value = "/comment/likeRemoval/{commentId}")
	public int unlikeComment(@PathVariable final long commentId)
	{
		return likeService.unlikeComment(commentId);
	}

	@PostMapping(value = "/picture/like/{pictureId}")
	public int likePicture(@PathVariable final long pictureId)
	{
		return likeService.likePicture(pictureId);
	}
	
	@PostMapping(value = "/picture/likeRemoval/{pictureId}")
	public int unlikePicture(@PathVariable final long pictureId)
	{
		return likeService.unlikePicture(pictureId);
	}
	
}
