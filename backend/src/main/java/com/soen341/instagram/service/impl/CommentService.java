package com.soen341.instagram.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.soen341.instagram.service.impl.FetchService;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
=======
>>>>>>> 935aa46024947fc2d069e5991b39fb96f410d546
import org.springframework.stereotype.Service;

import com.soen341.instagram.dao.impl.CommentRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.exception.comment.CommentLengthTooLongException;
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
	private PictureRepository pictureRepository;
<<<<<<< HEAD
	@Autowired
	@Qualifier("UserDetailsService")
	private UserDetailsService userDetailsService;
	@Autowired
	private FetchService fetch;
=======
>>>>>>> 935aa46024947fc2d069e5991b39fb96f410d546

	private static int maxCommentLength = 250;

	public Comment createComment(final String commentContent, final long pictureId)
	{
		if (commentContent.length() > maxCommentLength)
		{
			throw new CommentLengthTooLongException("Comment length exceeds " + maxCommentLength + " characters");
		}

<<<<<<< HEAD
		final Account account = fetch.getCurrentUser();
=======
		final Account account = UserAccessor.getCurrentAccount(accountRepository);
>>>>>>> 935aa46024947fc2d069e5991b39fb96f410d546
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

<<<<<<< HEAD
=======
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

>>>>>>> 935aa46024947fc2d069e5991b39fb96f410d546
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

<<<<<<< HEAD
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

=======
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
>>>>>>> 935aa46024947fc2d069e5991b39fb96f410d546
}
