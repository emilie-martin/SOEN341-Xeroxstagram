package com.soen341.instagram.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Picture extends Likable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @NotNull
    private Account account;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date created;

    @NotNull
    private String filePath;

    private String caption;

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

    //like c0de
//    @ManyToMany
//    Set<Account> likedBy;
//    
//    @NotNull
//    public Set<Account> getLikedBy() {
//		if (likedBy.equals(null))
//			return new HashSet<Account>();
//		else
//			return likedBy;
//	}
//	
//	public int getLikeCount() {
//		return likedBy.size();
//	}
    
}
