package com.demo.bookcab.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.bookcab.entity.UserAccount;

@Repository
public interface AccountDetailsRepository extends CrudRepository<UserAccount, Integer> {

	@Query("SELECT ba FROM UserAccount ba WHERE ba.user_name = :userName")
	UserAccount getUserAccountDetails(@Param("userName") String userName);

}
