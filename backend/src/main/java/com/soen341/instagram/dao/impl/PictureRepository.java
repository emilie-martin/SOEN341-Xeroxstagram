package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Picture;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PictureRepository extends PagingAndSortingRepository<Picture, Long> {
}
