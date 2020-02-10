package com.soen341.instagram.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.CommentRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.exception.comment.CommentLengthTooLongException;
import com.soen341.instagram.exception.comment.CommentNotFoundException;
import com.soen341.instagram.exception.picture.PictureNotFoundException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Comment;
import com.soen341.instagram.model.Picture;

@Service("commentService")
public class CommentService
{
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private PictureRepository pictureRepository;
	@Autowired
	@Qualifier("UserDetailsService")
	private UserDetailsService userDetailsService;

	private static int maxCommentLength = 250;

	public Comment createComment(final String commentContent, final long pictureId)
	{
		if (commentContent.length() > maxCommentLength)
		{
			throw new CommentLengthTooLongException("Comment length exceeds " + maxCommentLength + " characters");
		}

		final Account account = getCurrentUser();
		final Optional<Picture> picture = pictureRepository.findById(pictureId);

		if (!picture.isPresent())
		{
			throw new PictureNotFoundException();
		}

		final Comment comment = new Comment();
		comment.setComment(commentContent);
		comment.setCreated(new Date());
		comment.setAccount(account);
		comment.setPicture(picture.get());

		commentRepository.save(comment);
		return comment;
	}

	public int likeComment(final long commentId)
	{
		final Comment comment = findComment(commentId);
		comment.setNumLikes(comment.getNumLikes() + 1);
		commentRepository.save(comment);
		return comment.getNumLikes();
	}

	public int unlikeComment(final long commentId)
	{
		final Comment comment = findComment(commentId);
		comment.setNumLikes(comment.getNumLikes() - 1);
		commentRepository.save(comment);
		return comment.getNumLikes();
	}

	public void deleteComment(final long commentId)
	{
		final Comment comment = findComment(commentId);
		commentRepository.delete(comment);
	}

	protected Comment editComment(final long commentId, final String newComment)
	{
		if (newComment.length() > maxCommentLength)
		{
			throw new CommentLengthTooLongException("Comment length exceeds " + maxCommentLength + " characters");
		}

		final Comment comment = findComment(commentId);
		comment.setComment(newComment);
		commentRepository.save(comment);
		return comment;
	}

	private Comment findComment(final long commentId)
	{
		Optional<Comment> commentOptional = commentRepository.findById(commentId);
		if (!commentOptional.isPresent())
		{
			throw new CommentNotFoundException();
		}
		return commentOptional.get();
	}

	// Get current user authenticated
	public Account getCurrentUser()
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
		{
			String username = userDetailsService.loadUserByUsername(((UserDetails) principal).getUsername())
					.getUsername();
			return accountRepository.findByUsername(username);
		}
		else
		{
			throw new IllegalStateException("No user authenticated");
		}
	}

}
