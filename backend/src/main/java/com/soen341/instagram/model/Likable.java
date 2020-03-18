package com.soen341.instagram.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Likable {
	@ManyToMany
	private Set<Account> likedBy;
	
	public Likable() {
		likedBy = new HashSet<Account>();
	}
	
	public Set<Account> getLikedBy() {
		if (likedBy == null)
			likedBy = new HashSet<Account>();
		return likedBy;
	}
	
	public int getLikeCount() {
		return getLikedBy().size();
	}
	
}
