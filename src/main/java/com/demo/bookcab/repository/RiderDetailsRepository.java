package com.demo.bookcab.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.bookcab.entity.Rider;

@Repository
public interface RiderDetailsRepository extends CrudRepository<Rider, Integer> {

	@Query("SELECT ba FROM Rider ba WHERE ba.user_name = :userName")
	Rider getRiderAccountDetails(@Param("userName") String userName);

}
