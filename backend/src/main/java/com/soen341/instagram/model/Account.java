package com.soen341.instagram.model;

import com.soen341.instagram.exception.account.AlreadyFollowingException;
import com.soen341.instagram.exception.account.SameAccountException;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
public class Account {
    @Id
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dateOfBirth;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date created;

    private String biography;

    @ManyToMany
    private Set<Account> following;

    @OneToOne
    private Picture profilePicture;



    public Set<Account> getFollowing() {
        // Never return a null object
        if (following == null) {
            following = new HashSet<>();
        }
        return following;
    }

    public void follow(Account otherAccount) {
        if (this.equals(otherAccount)) {
            throw new SameAccountException("You cannot follow yourself.");
        } else if (getFollowing().contains(otherAccount)) {
            throw new AlreadyFollowingException("You are already following this user.");
        }
        getFollowing().add(otherAccount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return username.equals(account.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
