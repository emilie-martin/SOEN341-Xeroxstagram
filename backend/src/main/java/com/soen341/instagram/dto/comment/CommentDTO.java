package com.soen341.instagram.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentDTO
{
	@NotNull
	private String comment;
}
