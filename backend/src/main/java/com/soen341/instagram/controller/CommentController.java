package com.soen341.instagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soen341.instagram.dto.comment.CommentDTO;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Comment;
import com.soen341.instagram.service.impl.CommentService;

@RestController
public class CommentController
{
	@Autowired
	CommentService commentService;

	@PostMapping(value = "/Comment/newComment/{pictureId}")
	public Comment addComment(CommentDTO commentDTO, @PathVariable long pictureId)
	{
		final Comment comment = commentService.createComment(commentDTO.getComment(), pictureId);
		return comment;
	}

	@GetMapping(value = "/testAccount")
	public Account test()
	{
		return commentService.getCurrentUser();
	}
}
