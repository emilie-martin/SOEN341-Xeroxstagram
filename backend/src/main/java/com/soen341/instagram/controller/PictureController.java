package com.soen341.instagram.controller;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.service.impl.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class PictureController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PictureService pictureService;

    @PostMapping(value = "/picture", headers = "Content-Type=multipart/form-data")
    @ResponseStatus(value = HttpStatus.CREATED)
    // cant use RequestBody with multipart, so have to use RequestParam
    public void uploadPicture(@RequestParam String caption,
                              @RequestParam MultipartFile picture) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        Account user = accountRepository.findByUsername(username);
        pictureService.uploadPicture(caption, picture, user);
    }

    @GetMapping(value = "/picture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPicture(@PathVariable String id) {
        return pictureService.loadPicture(id);
    }
}
