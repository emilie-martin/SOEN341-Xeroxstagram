package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Picture;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends PagingAndSortingRepository<Picture, Long>
{
	List<Picture> findByAccount(Account account);
}
