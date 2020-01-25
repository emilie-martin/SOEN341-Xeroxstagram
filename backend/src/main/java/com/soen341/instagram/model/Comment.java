package com.soen341.instagram.model;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Comment {
    @Id
    private long id;
    private int numLikes;
    private int pictureId;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date created;
}
