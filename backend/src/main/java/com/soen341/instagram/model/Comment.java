package com.soen341.instagram.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Comment {
    @Id
    private int id;
    private int numLikes;
    private int pictureId;
}
