package com.soen341.instagram.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id; // to be discussed: file path or save picture in DB directly?

    @ManyToOne
    @NotNull
    private Account account;

    @Temporal(TemporalType.TIME)
    @NotNull
    private Date created;

    @NotNull
    private String filePath;

    private String caption;

    @ManyToMany
    private Set<Account> likedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Set<Account> getLikedBy() {
        // Never return a null object
        if (likedBy == null) {
            likedBy = new HashSet<>();
        }
        return likedBy;
    }
}
