package com.soen341.instagram.controller;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dto.picture.PictureDTO;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.model.Picture;
import com.soen341.instagram.service.impl.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PictureController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PictureService pictureService;

    @PostMapping(value = "/picture", consumes = {"multipart/form-data"})
    @ResponseStatus(value = HttpStatus.CREATED)
    // cant use RequestBody with multipart, so have to use RequestParam
    public PictureDTO uploadPicture(@RequestParam(required = false) String caption,
                              @RequestParam MultipartFile picture) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        Account user = accountRepository.findByUsername(username);
        Picture newPic = pictureService.uploadPicture(caption, picture, user);
        return pictureService.toPictureDTO(newPic);
    }

    @GetMapping(value = "/picture/{id}")
    public PictureDTO getPicture(@PathVariable String id) {
        return pictureService.getPictureDTOFromId(id);
    }

    @GetMapping(value = "/picture/{id}.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPictureFile(@PathVariable String id) {
        return pictureService.loadPicture(id);
    }

    @GetMapping(value = "/{username}/pictures")
    public List<Long> getAccountPictures(@PathVariable String username) {
        return pictureService.getAccountPictures(accountRepository.findByUsername(username));
    }
}
