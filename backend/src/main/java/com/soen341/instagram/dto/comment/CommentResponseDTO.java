package com.soen341.instagram.dto.comment;

import java.util.Date;

import com.soen341.instagram.dto.picture.PictureDTO;

public class CommentResponseDTO {
	private long id;
	private String comment;
	private Date created;
	private String account;
	private PictureDTO pictureDTO;
	private int nbLikes;
	private boolean editable;
	private int likeCount;

	public int getNbLikes()
	{
		return nbLikes;
	}

	public void setNbLikes(int nbLikes)
	{
		this.nbLikes = nbLikes;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public PictureDTO getPictureDTO()
	{
		return pictureDTO;
	}

	public void setPictureDTO(PictureDTO pictureDTO)
	{
		this.pictureDTO = pictureDTO;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public Date getCreated()
	{
		return created;
	}

	public void setCreated(Date created)
	{
		this.created = created;
	}

	public boolean isEditable()
	{
		return editable;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public int getLikeCount()
	{
		return likeCount;
	}

	public void setLikeCount(int likeCount)
	{
		this.likeCount = likeCount;
	}

}
