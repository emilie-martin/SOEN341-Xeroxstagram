package com.soen341.instagram.dao.impl;

import java.util.List;

// Sprint Boot
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

// Project
import com.soen341.instagram.model.Comment;
import com.soen341.instagram.model.Picture;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long>
{
	List<Comment> findByPicture(Picture picture);
}
