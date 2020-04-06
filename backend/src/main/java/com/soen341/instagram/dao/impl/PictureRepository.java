package com.soen341.instagram.dao.impl;

import java.util.List;

// Sprint Boot
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

// Project
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Picture;

@Repository
public interface PictureRepository extends PagingAndSortingRepository<Picture, Long>
{
	List<Picture> findByAccount(Account account);
}
