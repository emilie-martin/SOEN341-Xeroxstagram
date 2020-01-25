package com.soen341.instagram.model;

import com.soen341.instagram.exception.SameAccountException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
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

    // Following field can be moved to a new "Profile" class. To be discussed
    private String biography;

    @ManyToMany
    private List<Account> following;

    // Following fields are redundant but could be more efficient. To be discussed
    private int numFollowers;
    private int numFollowing;
    private int numPosts;

    @OneToOne
    private Picture profilePicture;


    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Account> getFollowing() {
        // Never return a null object
        if (this.following == null) {
            this.following = new ArrayList<>();
        }
        return this.following;
    }

    public void follow(Account otherAccount) {
        if (this.username.equals(otherAccount.username)) {
            throw new SameAccountException("You cannot follow yourself.");
        }
        this.getFollowing().add(otherAccount);
    }
}
