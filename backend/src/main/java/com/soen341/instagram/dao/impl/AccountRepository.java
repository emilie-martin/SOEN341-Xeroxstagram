package com.soen341.instagram.dao.impl;

import com.soen341.instagram.model.Account;

// Sprint Boot
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, String>
{
	Account findByUsername(String username);

	Account findByEmail(String email);

	@Query(value = "SELECT EXISTS(SELECT * FROM account_following AS follow "
			+ "WHERE follow.account_username = :currentUser AND follow.following_username = :accountToFollow "
			+ "LIMIT 1);", nativeQuery = true)
	int doesUserFollow(@Param("currentUser") String currentUser, @Param("accountToFollow") String accountToFollow);
}
