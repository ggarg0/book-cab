package com.demo.bookcab.service.business.locationdetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookcab.data.service.LocationDetailsDataService;
import com.demo.bookcab.dto.LocationFootfallResponse;
import com.demo.bookcab.entity.Locations;

import lombok.Data;

@Service
@Data
public class LocationDetailsServiceImpl implements LocationDetailsService {

	@Autowired
	private LocationDetailsDataService locationDetailsDataService;

	@Override
	public List<Locations> getLocationDetails(String pickup, String drop) {
		return this.locationDetailsDataService.getLocationDetails(pickup, drop);
	}

	@Override
	public List<LocationFootfallResponse> getAllLocationFootfall() {
		return this.locationDetailsDataService.getAllLocationFootfall();
	}

	@Override
	public void saveAllLocationDetails(List<Locations> locationDetails) {
		this.locationDetailsDataService.saveAllLocationDetails(locationDetails);
	}

}
