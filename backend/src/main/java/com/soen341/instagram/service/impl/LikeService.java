package com.soen341.instagram.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.soen341.instagram.dao.impl.CommentRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.exception.like.MultipleLikeException;
import com.soen341.instagram.exception.like.NoLikeException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Comment;
import com.soen341.instagram.model.Picture;

public class LikeService {
	@Autowired
	private FetchService fetch;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PictureRepository pictureRepository;
	
	public int likeComment(final long commentId)
	{
		final Comment comment = fetch.getCommentFromId(commentId);
		final Set<Account> likedBy = comment.getLikedBy();
		final boolean addedSuccessfully = likedBy.add(fetch.getCurrentUser());
		if (!addedSuccessfully)
		{
			throw new MultipleLikeException("A comment can only be liked once by the same user.");
		}

		commentRepository.save(comment);

		return comment.getLikeCount();
	}

	public int unlikeComment(final long commentId)
	{
		final Comment comment = fetch.getCommentFromId(commentId);
		final Set<Account> likedBy = comment.getLikedBy();
		final boolean removedSuccessfully = likedBy.remove(fetch.getCurrentUser());

		if (!removedSuccessfully)
		{
			throw new NoLikeException("The comment has not been liked by the user");
		}

		commentRepository.save(comment);

		return comment.getLikeCount();
	}
	
	public int likePicture(final long pictureId) {
		final Picture picture = fetch.getPictureFromId(pictureId);
		final Set<Account> likedBy = picture.getLikedBy();
		final boolean liked = likedBy.add(fetch.getCurrentUser());
		
		if (!liked) {
			throw new MultipleLikeException("A picture can only be liked once by the same user.");
		}
		
		pictureRepository.save(picture);
		
		return picture.getLikeCount();
		
	}
	
	public int unlikePicture(final long pictureId) {
		final Picture picture = fetch.getPictureFromId(pictureId);
		final Set<Account> likedBy = picture.getLikedBy();
		final boolean unliked = likedBy.remove(fetch.getCurrentUser());
		
		if (!unliked) {
			throw new NoLikeException("This picture has not yet been liked by the user.");
		}
		
		pictureRepository.save(picture);
		
		return picture.getLikeCount();
		
	}
	
}
