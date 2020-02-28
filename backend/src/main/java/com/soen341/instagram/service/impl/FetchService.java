package com.soen341.instagram.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.CommentRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.exception.comment.CommentNotFoundException;
import com.soen341.instagram.exception.picture.InvalidIdException;
import com.soen341.instagram.exception.picture.PictureNotFoundException;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Comment;
import com.soen341.instagram.model.Picture;

public class FetchService {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PictureRepository pictureRepository;
	
	@Autowired
	@Qualifier("UserDetailsService")
	private UserDetailsService userDetailsService;
	
	Account getCurrentUser()
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
	
	public Comment getCommentFromId(final long commentId)
	{
		Optional<Comment> commentOptional = commentRepository.findById(commentId);
		if (!commentOptional.isPresent()) 
		{
			throw new CommentNotFoundException("The specified comment does not exist.");
		}
		return commentOptional.get();
	}
	
	public Comment getCommentFromId(final String id)
	{
		long commentId;
        try {
            commentId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new InvalidIdException("Invalid comment ID.");
        }
        return getCommentFromId(commentId);
	}
	
	public Picture getPictureFromId(final long pictureId) {
		
        Optional<Picture> optionalPic = pictureRepository.findById(pictureId);
        if (!optionalPic.isPresent()) 
        {
            throw new PictureNotFoundException("The specified picture does not exist.");
        }
        return optionalPic.get();
    }
	
	public Picture getPictureFromId(final String id) {
        long pictureId;
        try {
            pictureId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new InvalidIdException("Invalid picture ID.");
        }
        return getPictureFromId(pictureId);
    }
	
}
