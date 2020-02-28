package com.soen341.instagram.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.soen341.instagram.service.impl.FetchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.soen341.instagram.dao.impl.CommentRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.exception.comment.CommentLengthTooLongException;
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
	private PictureRepository pictureRepository;
	@Autowired
	@Qualifier("UserDetailsService")
	private UserDetailsService userDetailsService;
	@Autowired
	private FetchService fetch;

	private static int maxCommentLength = 250;

	public Comment createComment(final String commentContent, final long pictureId)
	{
		if (commentContent.length() > maxCommentLength)
		{
			throw new CommentLengthTooLongException("Comment length exceeds " + maxCommentLength + " characters");
		}

		final Account account = fetch.getCurrentUser();
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

	public void deleteComment(final long commentId)
	{
		final Comment comment = fetch.getCommentFromId(commentId);
		commentRepository.delete(comment);
	}

	public Comment editComment(final long commentId, final String newComment)
	{
		if (newComment.length() > maxCommentLength)
		{
			throw new CommentLengthTooLongException("Comment length exceeds " + maxCommentLength + " characters");
		}

		final Comment comment = fetch.getCommentFromId(commentId);
		comment.setComment(newComment);
		commentRepository.save(comment);
		return comment;
	}

	public List<Comment> getCommentsByPicture(final long pictureId)
	{
		final Picture picture = fetch.getPictureFromId(pictureId);
		return commentRepository.findByPicture(picture);
	}

//	private Picture findPicture(final long pictureId)
//	{
//		Optional<Picture> pictureOptional = pictureRepository.findById(pictureId);
//		if (!pictureOptional.isPresent())
//		{
//			throw new InvalidIdException("Picture Id is invalid");
//		}
//		return pictureOptional.get();
//	}
//
//	public Comment findComment(final long commentId)
//	{
//		Optional<Comment> commentOptional = commentRepository.findById(commentId);
//		if (!commentOptional.isPresent())
//		{
//			throw new CommentNotFoundException();
//		}
//		return commentOptional.get();
//	}

	// Get current user authenticated, maybe create a new class that handle this
	// since it will be used by multiple service
//	private Account getCurrentUser()
//	{
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		if (principal instanceof UserDetails)
//		{
//			String username = userDetailsService.loadUserByUsername(((UserDetails) principal).getUsername())
//					.getUsername();
//			return accountRepository.findByUsername(username);
//		}
//		else
//		{
//			throw new IllegalStateException("No user authenticated");
//		}
//	}

}
