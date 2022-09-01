package com.demo.bookcab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.bookcab.entity.Trips;

@Repository
public interface TripDetailsRepository extends CrudRepository<Trips, Integer> {

	@Query("SELECT tp FROM Trips tp WHERE tp.booking_id = :bookingId")
	Trips getTripDetailsByBookingId(@Param("bookingId") long bookingId);

	@Query("SELECT tp FROM Trips tp WHERE tp.cab_number = :cabNumber")
	List<Trips> getTripDetailsByCabNumber(@Param("cabNumber") String cabNumber);

	@Query("SELECT tp FROM Trips tp WHERE tp.user_name = :username")
	List<Trips> getTripDetailsByUserName(@Param("username") String username);
}
