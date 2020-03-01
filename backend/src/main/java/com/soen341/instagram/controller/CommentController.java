package com.soen341.instagram.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dao.impl.PictureRepository;
import com.soen341.instagram.dto.comment.CommentDTO;
import com.soen341.instagram.dto.comment.CommentResponseDTO;
import com.soen341.instagram.dto.picture.PictureDTO;
import com.soen341.instagram.model.Comment;
import com.soen341.instagram.model.Picture;
import com.soen341.instagram.service.impl.CommentService;

@RestController
public class CommentController
{
	@Autowired
	private CommentService commentService;
	@Autowired
	PictureRepository rep;
	@Autowired
	AccountRepository acrep;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/comment/newComment/{pictureId}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public CommentResponseDTO addComment(@Valid @RequestBody final CommentDTO commentDTO,
			@PathVariable final long pictureId)
	{
		final Comment comment = commentService.createComment(commentDTO.getComment(), pictureId);
		final CommentResponseDTO commentResponse = convertCommentIntoDTO(comment);
		return commentResponse;
	}

	@PostMapping(value = "/comment/like/{commentId}")
	public int likeComment(@PathVariable final long commentId)
	{
		return commentService.likeComment(commentId);
	}

	@PostMapping(value = "/comment/likeRemoval/{commentId}")
	public int unlikeComment(@PathVariable final long commentId)
	{
		return commentService.unlikeComment(commentId);
	}

	@DeleteMapping(value = "/comment/commentRemoval/{commentId}")
	public void deleteComment(@PathVariable final long commentId)
	{
		commentService.deleteComment(commentId);
	}

	// Discuss what we should return
	@PutMapping(value = "/comment/commentUpdate/{commentId}")
	public CommentResponseDTO updateComment(@Valid @RequestBody final CommentDTO commentDTO,
			@PathVariable long commentId)
	{
		final Comment comment = commentService.editComment(commentId, commentDTO.getComment());
		return convertCommentIntoDTO(comment);
	}

	@GetMapping(value = "/comment/commentById/{commentId}")
	public CommentResponseDTO getCommentById(@PathVariable final long commentId)
	{
		final Comment comment = commentService.findComment(commentId);
		return convertCommentIntoDTO(comment);
	}

	@GetMapping(value = "/comment/commentByPicture/{pictureId}")
	public List<CommentResponseDTO> getCommentsByPicture(@PathVariable long pictureId)
	{
		final List<Comment> comments = commentService.getCommentsByPicture(pictureId);
		final List<CommentResponseDTO> commentsResponseDTO = commentService.determineEditable(comments);

		return commentsResponseDTO;
	}

	private CommentResponseDTO convertCommentIntoDTO(final Comment comment)
	{
		final CommentResponseDTO commentResponseDTO = modelMapper.map(comment, CommentResponseDTO.class);
		commentResponseDTO.setNbLikes(comment.getLikedBy().size());
		// Creating picture DTO
		final Picture picture = comment.getPicture();
		final PictureDTO pictureDTO = modelMapper.map(picture, PictureDTO.class);
		pictureDTO.setAccount(picture.getAccount().getUsername());

		commentResponseDTO.setPictureDTO(pictureDTO);
		commentResponseDTO.setAccount(comment.getAccount().getUsername());

		return commentResponseDTO;
	}
}
