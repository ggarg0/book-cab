package com.demo.bookcab.service.business.cabdetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookcab.constant.MessageConstants;
import com.demo.bookcab.data.service.CabDetailsDataService;
import com.demo.bookcab.dto.CabDetailsResponse;
import com.demo.bookcab.entity.Cabs;
import com.demo.bookcab.exceptions.CabDetailsNotFoundException;

import lombok.Data;

@Service
@Data
public class CabDetailsServiceImpl implements CabDetailsService {

	@Autowired
	private CabDetailsDataService cabDetailsDataService;

	@Override
	public CabDetailsResponse getCabDetailsByCabNumber(String cabNumber) {

		Cabs cab = null;
		try {
			cab = this.cabDetailsDataService.getCabDetailsByCabNumber(cabNumber).get(0);

		} catch (CabDetailsNotFoundException exp) {
			return new CabDetailsResponse(cabNumber, null, null, 0.0, null, MessageConstants.CabDetailsNotFound);
		}

		return new CabDetailsResponse(cab.getCab_number(), cab.getCab_name(), cab.getDriver_name(), cab.getRate(),
				cab.getStatus(), "");
	}

	@Override
	public List<Cabs> getCabDetailsByCabName(String cabName) {
		return this.cabDetailsDataService.getCabDetailsByCabName(cabName);
	}

	@Override
	public List<Cabs> getAllCabDetails() {
		return this.cabDetailsDataService.getAllCabDetails();
	}

	@Override
	public void saveAllCabDetails(List<Cabs> cabsDetails) {
		this.cabDetailsDataService.saveAllCabDetails(cabsDetails);
	}

}
