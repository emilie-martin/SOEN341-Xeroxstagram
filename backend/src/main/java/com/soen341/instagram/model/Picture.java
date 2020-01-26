package com.soen341.instagram.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id; // to be discussed: file path or save picture in DB directly?

    // following fields need to be discussed
    private int numLikes;
    private int numComments;

    @ManyToOne
    @NotNull
    private Account account;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date created;

    private String caption;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
