package com.demo.bookcab.service.business.tripdetails;

import java.util.List;

import com.demo.bookcab.dto.RiderInquiry;
import com.demo.bookcab.dto.TripDetailsResponse;
import com.demo.bookcab.entity.Trips;

public interface TripDetailsService {

	/**
	 * <p>
	 * This method should return the trip details for entered username
	 * </p>
	 * <br>
	 * 
	 * @param riderInquiry : RiderInquiry
	 * @return {List of @link TripDetailsResponse}
	 * 
	 */
	List<TripDetailsResponse> getTripDetailsByUserName(RiderInquiry bookingIdRequest);
	
	/**
	 * <p>
	 * This method should return the details of all trips as list of Trips.
	 * </p>
	 * <br>
	 * 
	 * @return {List of @link Trips}
	 */
	List<Trips> getAllTripsDetails();

}
