package com.demo.bookcab.data.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookcab.constant.MessageConstants;
import com.demo.bookcab.entity.Cabs;
import com.demo.bookcab.entity.Trips;
import com.demo.bookcab.exceptions.CabDetailsNotFoundException;
import com.demo.bookcab.repository.CabDetailsRepository;

@Service
public class CabDetailsDataService {

	@Autowired
	private CabDetailsRepository cabDetailsRepository;

	public List<Cabs> getCabDetailsByCabNumber(String cabNumber) {
		List<Cabs> cabDetails = new ArrayList<>();
		cabDetails = this.cabDetailsRepository.getCabDetailsByCabNumber(cabNumber);

		if (cabDetails.size() != 1) {
			throw new CabDetailsNotFoundException(MessageConstants.CabDetailsNotFound);
		}

		return cabDetails;
	}

	public List<Cabs> getCabDetailsByCabName(String cabName) {
		List<Cabs> cabDetails = new ArrayList<>();

		cabDetails = getAllCabDetails().stream().filter(cab -> cab.getCab_name().equalsIgnoreCase(cabName)
				&& cab.getStatus().equalsIgnoreCase(MessageConstants.Available)).collect(Collectors.toList());

		if (cabDetails.isEmpty()) {
			throw new CabDetailsNotFoundException(MessageConstants.CabDetailsNotFound);
		}

		return cabDetails;
	}

	public List<Cabs> getAllCabDetails() {
		List<Cabs> cabDetails = new ArrayList<>();
		this.cabDetailsRepository.findAll().forEach(cab -> {
			cabDetails.add(cab);
		});
		return cabDetails;
	}

	public void saveAllCabDetails(List<Cabs> cabDetails) {
		this.cabDetailsRepository.saveAll(cabDetails);
	}

	public void saveCabDetails(Cabs cabDetails) {
		this.cabDetailsRepository.save(cabDetails);
	}

}
