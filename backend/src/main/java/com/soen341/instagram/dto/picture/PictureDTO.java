package com.soen341.instagram.dto.picture;

import lombok.Data;

import java.util.Date;

@Data
public class PictureDTO {
    private long id;
    private String caption;
    private Date created;
    private String account;
}