package com.demo.bookcab.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookcab.entity.Rider;
import com.demo.bookcab.repository.RiderDetailsRepository;

@Service
public class RiderDetailsDataService {

	@Autowired
	private RiderDetailsRepository riderDetailsRepository;

	/**
	 * @param userName
	 * @return Account holder if account exists, else returns null
	 */
	public Rider getRiderAccountDetails(String userName) {
		return riderDetailsRepository.getRiderAccountDetails(userName);
	}

	public List<Rider> getAllRiderAccountDetails() {
		List<Rider> riderDetails = new ArrayList<>();
		this.riderDetailsRepository.findAll().forEach(rider -> {
			rider.setWallet_pin(null);
			riderDetails.add(rider);
		});
		return riderDetails;
	}

	public void saveAllRiderAccountDetails(List<Rider> riderDetails) {
		this.riderDetailsRepository.saveAll(riderDetails);
	}

	public void saveRiderAccountDetails(Rider riderDetails) {
		this.riderDetailsRepository.save(riderDetails);
	}

}
