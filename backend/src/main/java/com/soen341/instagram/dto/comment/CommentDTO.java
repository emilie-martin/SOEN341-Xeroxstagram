package com.soen341.instagram.dto.comment;

import javax.validation.constraints.NotBlank;

public class CommentDTO
{
	@NotBlank
	private String comment;

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}
}
