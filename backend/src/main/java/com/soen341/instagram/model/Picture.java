package com.soen341.instagram.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

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

    public Set<Account> getLikedBy() {
        // Never return a null object
        if (likedBy == null) {
            likedBy = new HashSet<>();
        }
        return likedBy;
    }
}
