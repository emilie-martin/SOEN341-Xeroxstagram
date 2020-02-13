package com.soen341.instagram.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String comment;

    @Temporal(TemporalType.TIME)
    @NotNull
    private Date created;

    @ManyToOne
    @NotNull
    private Account account;

    @ManyToOne
    @NotNull
    private Picture picture;

    @ManyToMany
    private Set<Account> likedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Account> getLikedBy() {
        // Never return a null object
        if (likedBy == null) {
            likedBy = new HashSet<>();
        }
        return likedBy;
    }
}
