package com.soen341.instagram.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
