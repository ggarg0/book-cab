package com.demo.bookcab.service.business.tripdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookcab.constant.MessageConstants;
import com.demo.bookcab.data.service.CabDetailsDataService;
import com.demo.bookcab.data.service.RiderDetailsDataService;
import com.demo.bookcab.data.service.TripDetailsDataService;
import com.demo.bookcab.dto.RiderInquiry;
import com.demo.bookcab.dto.TripDetailsResponse;
import com.demo.bookcab.entity.Cabs;
import com.demo.bookcab.entity.Rider;
import com.demo.bookcab.entity.Trips;
import com.demo.bookcab.exceptions.RiderNotFoundException;
import com.demo.bookcab.exceptions.TripDetailsNotFoundException;
import com.demo.bookcab.security.AuthenticationService;

import lombok.Setter;

@Service
@Setter
public class TripDetailsServiceImpl implements TripDetailsService {

	@Autowired
	private TripDetailsDataService tripDetailsDataService;

	@Autowired
	private RiderDetailsDataService riderDetailsDataService;

	@Autowired
	private CabDetailsDataService cabDetailsDataService;

	@Autowired
	AuthenticationService authenticationService;

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Override
	public List<TripDetailsResponse> getTripDetailsByUserName(RiderInquiry riderInquiry) {
		List<TripDetailsResponse> tripResponse = new ArrayList<TripDetailsResponse>();

		if (Objects.isNull(riderInquiry)) {
			tripResponse.add(new TripDetailsResponse(null, null, null, null, null, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					null, null, MessageConstants.BookingRequestNotValid));
			return tripResponse;
		}
		try {
			logger.info("Fetching rider details from database");
			Rider rider = riderDetailsDataService.getRiderAccountDetails(riderInquiry.getUserName());

			if (Objects.isNull(rider)) {
				throw new RiderNotFoundException(MessageConstants.RiderNotFound);
			}

			if (this.authenticationService.authenticateRiderAccount(rider, riderInquiry.getWalletPin())) {
				logger.info("Fetching trip details from database");
				List<Trips> trips = tripDetailsDataService.getTripDetailsByUserName(riderInquiry.getUserName());

				if (Objects.isNull(trips) || trips.isEmpty()) {
					throw new TripDetailsNotFoundException(MessageConstants.TripDetailsNotFound);
				}

				for (Trips trip : trips) {
					logger.info("Fetching cabs details from trip");
					Cabs cabSelected = cabDetailsDataService.getCabDetailsByCabNumber(trip.getCab_number()).get(0);

					tripResponse.add(new TripDetailsResponse(riderInquiry.getUserName(), cabSelected.getCab_name(),
							cabSelected.getCab_number(), cabSelected.getDriver_name(), trip.getLocation_pickup(),
							trip.getLocation_drop(), trip.getDistance(), cabSelected.getRate(), trip.getFare(),
							rider.getWallet_balance(), rider.getWallet_balance_onhold(), 0.0, trip.getStatus(),
							trip.getBooking_id(), ""));
				}
			} else {
				tripResponse.add(new TripDetailsResponse(riderInquiry.getUserName(), null, null, null, null, null, 0.0,
						0.0, 0.0, 0.0, 0.0, 0.0, null, null, MessageConstants.InvalidPin));
			}
		} catch (RiderNotFoundException riderException) {
			logger.error(MessageConstants.RiderNotFound);
			tripResponse.add(new TripDetailsResponse(riderInquiry.getUserName(), null, null, null, null, null, 0.0, 0.0,
					0.0, 0.0, 0.0, 0.0, null, null, MessageConstants.RiderNotFound));
		} catch (TripDetailsNotFoundException tripException) {
			logger.error(MessageConstants.TripDetailsNotFound);
			tripResponse.add(new TripDetailsResponse(riderInquiry.getUserName(), null, null, null, null, null, 0.0, 0.0,
					0.0, 0.0, 0.0, 0.0, null, null, MessageConstants.TripDetailsNotFound));
		}
		return tripResponse;
	}

	@Override
	public List<Trips> getAllTripsDetails() {
		return this.tripDetailsDataService.getAllTripDetails();
	}

}
