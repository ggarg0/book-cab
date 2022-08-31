package com.demo.bookcab.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.bookcab.entity.Trips;


@Repository
public interface TripDetailsRepository extends CrudRepository<Trips, Integer> {


}
