package com.demo.bookcab.service.business.cabdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookcab.constant.MessageConstants;
import com.demo.bookcab.data.service.CabDetailsDataService;
import com.demo.bookcab.dto.CabDetailsResponse;
import com.demo.bookcab.dto.CabNameRequest;
import com.demo.bookcab.dto.CabNumberRequest;
import com.demo.bookcab.entity.Cabs;
import com.demo.bookcab.exceptions.CabDetailsNotFoundException;

import lombok.Data;

@Service
@Data
public class CabDetailsServiceImpl implements CabDetailsService {

	@Autowired
	private CabDetailsDataService cabDetailsDataService;

	@Override
	public CabDetailsResponse getCabDetailsByCabNumber(CabNumberRequest cabNumber) {
		if (Objects.isNull(cabNumber)) {
			return (new CabDetailsResponse(cabNumber.getCabNumber(), null, null, 0.0, null,
					MessageConstants.InvalidCarNumber));
		}

		Cabs cab = null;
		try {
			cab = this.cabDetailsDataService.getCabDetailsByCabNumber(cabNumber.getCabNumber()).get(0);

		} catch (CabDetailsNotFoundException exp) {
			return new CabDetailsResponse(cabNumber.getCabNumber(), null, null, 0.0, null,
					MessageConstants.CabDetailsNotFound);
		}

		return new CabDetailsResponse(cab.getCab_number(), cab.getCab_name(), cab.getDriver_name(), cab.getRate(),
				cab.getStatus(), "");
	}

	@Override
	public List<CabDetailsResponse> getCabDetailsByCabName(CabNameRequest cabName) {
		List<Cabs> cabs = null;
		List<CabDetailsResponse> cabsResponse = new ArrayList<CabDetailsResponse>();

		if (Objects.isNull(cabName)) {
			cabsResponse.add(new CabDetailsResponse(null, cabName.getCabName(), null, 0.0, null,
					MessageConstants.InvalidCarName));
			return cabsResponse;
		}

		try {
			cabs = this.cabDetailsDataService.getCabDetailsByCabName(cabName.getCabName());

		} catch (CabDetailsNotFoundException exp) {
			cabsResponse.add(new CabDetailsResponse(null, cabName.getCabName(), null, 0.0, null,
					MessageConstants.CabDetailsNotFound));
			return cabsResponse;
		}

		for (Cabs cab : cabs) {
			cabsResponse.add(new CabDetailsResponse(cab.getCab_number(), cab.getCab_name(), cab.getDriver_name(),
					cab.getRate(), cab.getStatus(), ""));
		}

		return cabsResponse;
	}

	@Override
	public List<Cabs> getAllCabDetails() {
		return this.cabDetailsDataService.getAllCabDetails();
	}

}
