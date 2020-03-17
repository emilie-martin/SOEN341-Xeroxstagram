package com.soen341.instagram.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.CommentRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.exception.comment.CommentLengthTooLongException;
import com.soen341.instagram.exception.comment.CommentNotFoundException;
import com.soen341.instagram.exception.like.MultipleLikeException;
import com.soen341.instagram.exception.like.NoLikeException;
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

	public void deleteComment(final String commentId)
	{
		final Comment comment = findComment(commentId);
		commentRepository.delete(comment);
	}

	public Comment editComment(final String commentId, final String newComment)
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

	public Comment findComment(final String id)
	{
	    long commentId;
        try {
            commentId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new InvalidIdException("Invalid comment ID.");
        }
		Optional<Comment> commentOptional = commentRepository.findById(commentId);
		if (!commentOptional.isPresent())
		{
			throw new CommentNotFoundException();
		}
		return commentOptional.get();
	}

	// like service
	public int likeComment(final String commentId)
	{
		final Comment comment = findComment(commentId);
		final Set<Account> likedBy = comment.getLikedBy();
		final boolean addedSuccessfully = likedBy.add(UserAccessor.getCurrentAccount(accountRepository));
		if (!addedSuccessfully)
		{
			throw new MultipleLikeException("You can only like this comment once.");
		}
		commentRepository.save(comment);
		return comment.getLikeCount();
	}

	public int unlikeComment(final String commentId)
	{
		final Comment comment = findComment(commentId);
		final Set<Account> likedBy = comment.getLikedBy();
		final boolean removedSuccessfully = likedBy.remove(UserAccessor.getCurrentAccount(accountRepository));
		if (!removedSuccessfully)
		{
			throw new NoLikeException("You have not liked this comment yet.");
		}
		commentRepository.save(comment);
		return comment.getLikeCount();
	}
	
	
}
