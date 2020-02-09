package com.soen341.instagram.controller;

import com.soen341.instagram.service.impl.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @PostMapping(value = "/picture", headers = "Content-Type=multipart/form-data")
    @ResponseStatus(value = HttpStatus.CREATED)
    // cant use RequestBody with multimedia
    public void uploadPicture(@RequestParam String caption,
                              @RequestParam MultipartFile picture) {
        pictureService.uploadPicture(caption, picture);
    }

    @GetMapping(value = "/picture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPicture(@PathVariable String id) {
        return pictureService.loadPicture(id);
    }
}
