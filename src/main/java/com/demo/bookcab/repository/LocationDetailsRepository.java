package com.demo.bookcab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.bookcab.entity.Locations;


@Repository
public interface LocationDetailsRepository extends CrudRepository<Locations, Integer> {

	@Query("SELECT loc FROM Locations loc WHERE loc.location_name IN (:pickup,:drop)")
	List<Locations> getLocationDetails(@Param("pickup") String pickup,
			@Param("drop") String drop);

}
