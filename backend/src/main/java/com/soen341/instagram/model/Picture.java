package com.soen341.instagram.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Picture {
    @Id
    private long id; // to be discussed: file path or save picture in DB directly?

    // following fields need to be discussed
    private int numLikes;
    private int numComments;

    @OneToOne
    @NotNull
    private Account accountId;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }
}
