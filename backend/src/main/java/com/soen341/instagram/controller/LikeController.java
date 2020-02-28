package com.soen341.instagram.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.soen341.instagram.service.impl.LikeService;

public class LikeController {
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

}
