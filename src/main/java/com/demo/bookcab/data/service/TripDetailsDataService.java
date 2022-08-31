package com.demo.bookcab.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookcab.entity.Trips;
import com.demo.bookcab.repository.TripDetailsRepository;

@Service
public class TripDetailsDataService {

	@Autowired
	private TripDetailsRepository tripDetailsRepository;

	public void saveAllRiderAccountDetails(List<Trips> tripDetails) {
		this.tripDetailsRepository.saveAll(tripDetails);
	}
	
	public void saveRiderAccountDetails(Trips tripDetails) {
		this.tripDetailsRepository.save(tripDetails);
	}


}
