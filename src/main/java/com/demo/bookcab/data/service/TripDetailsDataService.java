package com.demo.bookcab.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookcab.constant.MessageConstants;
import com.demo.bookcab.entity.Trips;
import com.demo.bookcab.exceptions.TripDetailsNotFoundException;
import com.demo.bookcab.repository.TripDetailsRepository;

@Service
public class TripDetailsDataService {

	@Autowired
	private TripDetailsRepository tripDetailsRepository;

	public Trips getTripDetailsByBookingId(long bookingId) {

		Trips trip = tripDetailsRepository.getTripDetailsByBookingId(bookingId);

		if (trip == null) {
			throw new TripDetailsNotFoundException(MessageConstants.TripDetailsNotFound);
		}

		return trip;
	}

	public List<Trips> getTripDetailsByUserName(String username) {
		List<Trips> tripDetails = new ArrayList<>();

		tripDetails = tripDetailsRepository.getTripDetailsByUserName(username);

		if (tripDetails.isEmpty()) {
			throw new TripDetailsNotFoundException(MessageConstants.TripDetailsNotFound);
		}

		return tripDetails;
	}

	public List<Trips> getAllTripDetails() {
		List<Trips> tripDetails = new ArrayList<>();
		this.tripDetailsRepository.findAll().forEach(trip -> {
			tripDetails.add(trip);
		});
		return tripDetails;
	}

	public void saveAllTripDetails(List<Trips> tripDetails) {
		this.tripDetailsRepository.saveAll(tripDetails);
	}

	public void saveTripDetails(Trips tripDetails) {
		this.tripDetailsRepository.save(tripDetails);
	}

}
