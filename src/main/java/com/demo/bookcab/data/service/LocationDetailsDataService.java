package com.demo.bookcab.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookcab.constant.MessageConstants;
import com.demo.bookcab.dto.LocationFootfallResponse;
import com.demo.bookcab.entity.Locations;
import com.demo.bookcab.exceptions.LocationDetailsNotFoundException;
import com.demo.bookcab.repository.LocationDetailsRepository;

@Service
public class LocationDetailsDataService {

	@Autowired
	private LocationDetailsRepository locationDetailsRepository;

	public List<LocationFootfallResponse> getAllLocationFootfall() {
		List<LocationFootfallResponse> locationDetails = new ArrayList<>();

		try {
			this.locationDetailsRepository.findAll().forEach(location -> {
				locationDetails.add(new LocationFootfallResponse(location.getLocation_name(), location.getPickup_count(),
						location.getDrop_count(), ""));
			});

			if (locationDetails.isEmpty()) {
				throw new LocationDetailsNotFoundException(MessageConstants.LocationDetailsNotFound);
			}
		} catch (LocationDetailsNotFoundException exp) {
			locationDetails.add(new LocationFootfallResponse(null, 0, 0, MessageConstants.LocationDetailsNotFound));
		}

		return locationDetails;
	}

	public List<Locations> getLocationDetails(String pickup, String drop) {
		List<Locations> locationDetails = new ArrayList<>();
		locationDetails = this.locationDetailsRepository.getLocationDetails(pickup, drop);

		if (locationDetails.size() != 2) {
			throw new LocationDetailsNotFoundException(MessageConstants.LocationDetailsNotFound);
		}

		return locationDetails;
	}

	public void saveAllLocationDetails(List<Locations> locationDetails) {
		this.locationDetailsRepository.saveAll(locationDetails);
	}
	
	public void saveLocationDetails(Locations locationDetails) {
		this.locationDetailsRepository.save(locationDetails);
	}

}
