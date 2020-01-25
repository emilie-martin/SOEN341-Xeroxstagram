package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
}
