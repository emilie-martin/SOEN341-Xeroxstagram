package com.soen341.instagram.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

public class Likable {
	private Reaction like;
	
	public Likable() {
		like = new Reaction();
	}
	
	public Set<Account> getLikedBy() {
		return like.getSet();
	}
	
	public int getLikeCount() {
		return like.getCount();
	}
	
}

class Reaction {	//implemented as "like" but this allows us to add other reactions i.e. dislikes, breads, etc...
	@ManyToMany
	@NotNull
	private Set<Account> reacted;
	
	public Reaction() {
		reacted = new HashSet<>();
	}
	
	public Set<Account> getSet() {
		return reacted;
	}
	
	public int getCount() {
		return reacted.size();
	}
	
}
