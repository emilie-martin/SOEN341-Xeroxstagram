package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Picture;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PictureRepository extends PagingAndSortingRepository<Picture, Long> {
    List<Picture> findByAccount(Account account);
}
