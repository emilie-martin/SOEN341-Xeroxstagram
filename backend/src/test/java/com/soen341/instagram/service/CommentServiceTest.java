package com.soen341.instagram.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.CommentRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.exception.comment.CommentLengthTooLongException;
import com.soen341.instagram.exception.picture.PictureNotFoundException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Comment;
import com.soen341.instagram.model.Picture;
import com.soen341.instagram.service.impl.CommentService;
import com.soen341.instagram.utils.UserAccessor;

@DataJpaTest
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

	private final static long pictureId = 1;
	private final static String validComment = "This is a valid comment";
	private final static Account account = Mockito.mock(Account.class);
	private final static Optional<Picture> Emptypicture = Optional.empty();
	private final static Optional<Picture> picture = Optional.of(new Picture());
	private static Comment comment;
	private static long commentId = 1;

	@Before
	public void setup()
	{
		comment = new Comment();
		comment.setAccount(account);
		comment.setComment(validComment);
		comment.setCreated(new Date());
		comment.setPicture(new Picture());
		comment.setId(commentId);

		PowerMockito.mockStatic(UserAccessor.class);
		PowerMockito.when(UserAccessor.getCurrentAccount(accountRepository)).thenReturn(account);
	}

	@Test(expected = CommentLengthTooLongException.class)
	public void createCommentLengthTooLong_ExpectCommentLengthTooLongException()
	{
		// final CommentService commentService = Mockito.mock(CommentService.class);
		final String commentLength273 = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
				+ "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
				+ "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
				+ "sssssssssssssssssssssssssssssssssssss";
		commentService.createComment(commentLength273, pictureId);
	}

	@Test(expected = PictureNotFoundException.class)
	public void createCommentWithInvalidPictureID_ExpectPictureNotFoundException()
	{
		Mockito.when(pictureRepository.findById(pictureId)).thenReturn(Emptypicture);
		commentService.createComment(validComment, pictureId);

	}

	@Test
	public void createCommentSuccessfully()
	{
		Mockito.when(pictureRepository.findById(pictureId)).thenReturn(picture);
		final Comment comment = commentService.createComment(validComment, pictureId);
		assertEquals(comment.getComment(), validComment);
	}

	@Test(expected = CommentLengthTooLongException.class)
	public void editComment_withLengthTooLong_ExpectCommentLengthTooLongException()
	{
		final String commentLength273 = "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
				+ "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
				+ "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
				+ "sssssssssssssssssssssssssssssssssssss";
		commentService.editComment(commentId, commentLength273);
	}

	@Test
	public void editCommentSuccessfully()
	{
		final String newComment = "This is a new comment";
		Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
		Mockito.when(account.getUsername()).thenReturn("username");
		commentRepository.save(comment);
		final Comment comment = this.commentService.editComment(commentId, newComment);
		assertEquals(comment.getComment(), newComment);
	}

}
