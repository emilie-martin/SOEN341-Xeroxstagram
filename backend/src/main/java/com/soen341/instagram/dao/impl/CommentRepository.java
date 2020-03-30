package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Comment;
import com.soen341.instagram.model.Picture;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long>
{
	List<Comment> findByPicture(Picture picture);
}
