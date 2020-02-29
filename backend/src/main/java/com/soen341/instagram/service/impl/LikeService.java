package com.soen341.instagram.service.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.CommentRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.exception.comment.CommentNotFoundException;
import com.soen341.instagram.exception.like.MultipleLikeException;
import com.soen341.instagram.exception.like.NoLikeException;
import com.soen341.instagram.exception.picture.PictureNotFoundException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Comment;
import com.soen341.instagram.model.Picture;

@Service("likeService")
public class LikeService {
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PictureRepository pictureRepository;
	@Autowired
	@Qualifier("UserDetailsService")
	private UserDetailsService userDetailsService;

	
	public int likeComment(final long commentId)
	{
		final Comment comment = getCommentFromId(commentId);
		final Set<Account> likedBy = comment.getLikedBy();
		final boolean addedSuccessfully = likedBy.add(getCurrentUser());
		if (!addedSuccessfully)
		{
			throw new MultipleLikeException("A comment can only be liked once by the same user.");
		}

		commentRepository.save(comment);
		System.out.println("\nYou have liked a comment. "+comment.getLikeCount());
		return comment.getLikeCount();
	}

	public int unlikeComment(final long commentId)
	{
		final Comment comment = getCommentFromId(commentId);
		final Set<Account> likedBy = comment.getLikedBy();
		final boolean removedSuccessfully = likedBy.remove(getCurrentUser());

		if (!removedSuccessfully)
		{
			throw new NoLikeException("The comment has not been liked by the user");
		}

		commentRepository.save(comment);

		return comment.getLikeCount();
	}
	
	public int likePicture(final long pictureId) {
		final Picture picture = getPictureFromId(pictureId);
		final Set<Account> likedBy = picture.getLikedBy();
		final boolean liked = likedBy.add(getCurrentUser());
		
		if (!liked) {
			throw new MultipleLikeException("A picture can only be liked once by the same user.");
		}
		
		pictureRepository.save(picture);
		
		return picture.getLikeCount();
		
	}
	
	public int unlikePicture(final long pictureId) {
		final Picture picture = getPictureFromId(pictureId);
		final Set<Account> likedBy = picture.getLikedBy();
		final boolean unliked = likedBy.remove(getCurrentUser());
		
		if (!unliked) {
			throw new NoLikeException("This picture has not yet been liked by the user.");
		}
		
		pictureRepository.save(picture);
		
		return picture.getLikeCount();
		
	}
	
	private Account getCurrentUser()
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
	
	private Comment getCommentFromId(final long commentId)
	{
		Optional<Comment> commentOptional = commentRepository.findById(commentId);
		if (!commentOptional.isPresent()) 
		{
			throw new CommentNotFoundException("The specified comment does not exist.");
		}
		return commentOptional.get();
	}
	
	private Picture getPictureFromId(final long pictureId) {
		
        Optional<Picture> optionalPic = pictureRepository.findById(pictureId);
        if (!optionalPic.isPresent()) 
        {
            throw new PictureNotFoundException("The specified picture does not exist.");
        }
        return optionalPic.get();
    }
	
	
}
