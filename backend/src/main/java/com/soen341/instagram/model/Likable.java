package com.soen341.instagram.model;

import java.util.Set;

public interface Likable {
	public Set<Account> getLikedBy();
	public int getLikeCount();
	
}
