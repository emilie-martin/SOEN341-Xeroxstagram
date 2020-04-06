package com.soen341.instagram.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

// Testing
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


// Project
import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.CommentRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.exception.comment.CommentLengthTooLongException;
import com.soen341.instagram.exception.picture.PictureNotFoundException;
import com.soen341.instagram.exception.like.MultipleLikeException;
import com.soen341.instagram.exception.like.NoLikeException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Comment;
import com.soen341.instagram.model.Picture;
import com.soen341.instagram.utils.UserAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserAccessor.class)
public class CommentServiceTest
{
	@InjectMocks
	private CommentService commentService;
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private CommentRepository commentRepository;
	@Mock
	private PictureRepository pictureRepository;

	private final static Account account = Mockito.mock(Account.class);

	private final static long PICTURE_ID = 1;
	private final static long COMMENT_ID = 1;
	private final static String VALID_COMMENT = "This is a valid comment";
	private final static Optional<Picture> EMPTY_PICTURE = Optional.empty();
	private final static Optional<Picture> PICTURE = Optional.of(new Picture());
	private final String COMMENT_LENGTH_273 = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
		+ "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
		+ "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
		+ "sssssssssssssssssssssssssssssssssssss";

	private static Comment comment;

	@Before
	public void setup()
	{
		comment = new Comment();
		comment.setAccount(account);
		comment.setComment(VALID_COMMENT);
		comment.setCreated(new Date());
		comment.setPicture(new Picture());
		comment.setId(COMMENT_ID);

		PowerMockito.mockStatic(UserAccessor.class);
		PowerMockito.when(UserAccessor.getCurrentAccount(accountRepository)).thenReturn(account);
	}

	@Test(expected = CommentLengthTooLongException.class)
	public void createCommentLengthTooLong_ExpectCommentLengthTooLongException()
	{
		commentService.createComment(COMMENT_LENGTH_273, PICTURE_ID);
	}

	@Test(expected = PictureNotFoundException.class)
	public void createCommentWithInvalidPictureID_ExpectPictureNotFoundException()
	{
		Mockito.when(pictureRepository.findById(PICTURE_ID)).thenReturn(EMPTY_PICTURE);

		commentService.createComment(VALID_COMMENT, PICTURE_ID);
	}

	@Test
	public void createCommentSuccessfully()
	{
		Mockito.when(pictureRepository.findById(PICTURE_ID)).thenReturn(PICTURE);

		final Comment comment = commentService.createComment(VALID_COMMENT, PICTURE_ID);

		assertEquals(comment.getComment(), VALID_COMMENT);
	}

	@Test(expected = CommentLengthTooLongException.class)
	public void editComment_withLengthTooLong_ExpectCommentLengthTooLongException()
	{
		final String commentLength273 = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
			+ "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
			+ "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
			+ "sssssssssssssssssssssssssssssssssssss";

		commentService.editComment(String.valueOf(COMMENT_ID), commentLength273);
	}

	@Test
	public void editCommentSuccessfully()
	{
		final String newComment = "This is a new comment";
		Mockito.when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(comment));
		Mockito.when(account.getUsername()).thenReturn("username");

		commentRepository.save(comment);
		
		final Comment comment = this.commentService.editComment(String.valueOf(COMMENT_ID), newComment);
		assertEquals(comment.getComment(), newComment);
	}

	@Test
	public void likeCommentSuccessfully()
	{
		Mockito.when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(comment));

		assertEquals(commentService.likeComment(String.valueOf(COMMENT_ID)), 1);
	}

	@Test(expected = MultipleLikeException.class)
	public void likeCommentMultipleTimes_ExpectMultipleLikeException()
	{
		Mockito.when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(comment));

		commentService.likeComment(String.valueOf(COMMENT_ID));
		commentService.likeComment(String.valueOf(COMMENT_ID));
	}

	@Test
	public void unlikeCommentSuccessfully()
	{
		// Mock having liked the comment previously
		final Set<Account> likedBy = comment.getLikedBy();
		likedBy.add(account);

		Mockito.when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(comment));

		assertEquals(comment.getLikedBy().size(), 1);
		assertEquals(commentService.unlikeComment(String.valueOf(COMMENT_ID)), 0);
	}

	@Test(expected = NoLikeException.class)
	public void unlikeNotLikedComment_ExpectNoLikeException()
	{
		Mockito.when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(comment));

		assertEquals(commentService.unlikeComment(String.valueOf(COMMENT_ID)), 0);
	}

	@Test
	public void getLikeStatus_ExpectTrue()
	{
		// Mock having liked the comment previously
		final Set<Account> likedBy = comment.getLikedBy();
		likedBy.add(account);

		Mockito.when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(comment));

		assertEquals(commentService.getLikeStatus(String.valueOf(COMMENT_ID)), true);
	}

	@Test
	public void getLikeStatus_ExpectFalse()
	{
		Mockito.when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(comment));

		assertEquals(commentService.getLikeStatus(String.valueOf(COMMENT_ID)), false);
	}
}
