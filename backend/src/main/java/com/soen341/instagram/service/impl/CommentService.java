package com.soen341.instagram.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.CommentRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.dto.comment.CommentResponseDTO;
import com.soen341.instagram.exception.comment.CommentLengthTooLongException;
import com.soen341.instagram.exception.comment.CommentNotFoundException;
import com.soen341.instagram.exception.comment.UnauthorizedRightsException;
import com.soen341.instagram.exception.like.MultipleLikeException;
import com.soen341.instagram.exception.picture.InvalidIdException;
import com.soen341.instagram.exception.picture.PictureNotFoundException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Comment;
import com.soen341.instagram.model.Picture;
import com.soen341.instagram.utils.UserAccessor;

@Service("commentService")
public class CommentService
{
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private PictureRepository pictureRepository;

	private static int maxCommentLength = 250;

	public Comment createComment(final String commentContent, final long pictureId)
	{
		if (commentContent.length() > maxCommentLength)
		{
			throw new CommentLengthTooLongException("Comment length exceeds " + maxCommentLength + " characters");
		}

		final Account account = UserAccessor.getCurrentAccount(accountRepository);
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
		final Set<Account> likedBy = comment.getLikedBy();
		final boolean addedSuccessfully = likedBy.add(UserAccessor.getCurrentAccount(accountRepository));
		if (!addedSuccessfully)
		{
			throw new MultipleLikeException("A comment can only be liked one time by the same user");
		}

		commentRepository.save(comment);

		return likedBy.size();
	}

	public int unlikeComment(final long commentId)
	{
		final Comment comment = findComment(commentId);
		final Set<Account> likedBy = comment.getLikedBy();
		final boolean removedSuccessfully = likedBy.remove(UserAccessor.getCurrentAccount(accountRepository));

		if (!removedSuccessfully)
		{
			throw new MultipleLikeException("The comment has not been liked by this user");
		}

		commentRepository.save(comment);

		return likedBy.size();
	}

	public void deleteComment(final long commentId)
	{
		final Comment comment = findComment(commentId);
		if (comment.getAccount().getUsername().equals(UserAccessor.getCurrentAccount(accountRepository).getUsername()))
		{
			commentRepository.delete(comment);
		}
		else
		{
			throw new UnauthorizedRightsException();
		}
	}

	public Comment editComment(final long commentId, final String newComment)
	{
		if (newComment.length() > maxCommentLength)
		{
			throw new CommentLengthTooLongException("Comment length exceeds " + maxCommentLength + " characters");
		}

		final Comment comment = findComment(commentId);

		if (comment.getAccount().getUsername().equals(UserAccessor.getCurrentAccount(accountRepository).getUsername()))
		{
			comment.setComment(newComment);
			commentRepository.save(comment);
		}
		else
		{
			throw new UnauthorizedRightsException();
		}

		return comment;
	}

	public List<Comment> getCommentsByPicture(final long pictureId)
	{
		final Picture picture = findPicture(pictureId);
		return commentRepository.findByPicture(picture);
	}

	private Picture findPicture(final long pictureId)
	{
		Optional<Picture> pictureOptional = pictureRepository.findById(pictureId);
		if (!pictureOptional.isPresent())
		{
			throw new InvalidIdException("Picture Id is invalid");
		}
		return pictureOptional.get();
	}

	public Comment findComment(final long commentId)
	{
		Optional<Comment> commentOptional = commentRepository.findById(commentId);
		if (!commentOptional.isPresent())
		{
			throw new CommentNotFoundException();
		}
		return commentOptional.get();
	}

	public CommentResponseDTO determineEditable(final CommentResponseDTO commentResponseDTO)
	{
		String currentUser = null;
		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))
		{
			final Account currentUserRequest = UserAccessor.getCurrentAccount(accountRepository);
			currentUser = currentUserRequest.getUsername();
		}

		// if current user matches the comment account or the picture account -> allow
		// editing
		if (currentUser != null && (commentResponseDTO.getAccount().equals(currentUser)
				|| commentResponseDTO.getPictureDTO().getAccount().equals(currentUser)))
		{
			commentResponseDTO.setEditable(true);
		}
		else
		{
			commentResponseDTO.setEditable(false);
		}
		return commentResponseDTO;
	}
}
