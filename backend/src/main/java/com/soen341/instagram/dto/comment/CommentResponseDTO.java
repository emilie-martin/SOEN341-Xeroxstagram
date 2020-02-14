package com.soen341.instagram.dto.comment;

import java.util.Date;

import com.soen341.instagram.dto.picture.PictureDTO;
import lombok.Data;

@Data
public class CommentResponseDTO
{
	private long id;
	private String comment;
	private Date created;
	private String account;
	private PictureDTO pictureDTO;

}
