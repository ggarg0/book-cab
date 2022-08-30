package com.demo.bookcab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.bookcab.entity.Cabs;

@Repository
public interface CabDetailsRepository extends CrudRepository<Cabs, Integer> {
		
	@Query("SELECT ca FROM Cabs ca WHERE ca.cab_number = :cabNumber")
	List<Cabs> getCabDetailsByCabNumber(@Param("cabNumber") String cabNumber);

}
