package com.demo.bookcab.service.business.riderdetails;

import java.util.List;

import com.demo.bookcab.dto.RiderInquiry;
import com.demo.bookcab.dto.RiderInquiryResponse;
import com.demo.bookcab.dto.BookingIdRequest;
import com.demo.bookcab.dto.TripDetailsResponse;
import com.demo.bookcab.dto.TripRequest;
import com.demo.bookcab.entity.Rider;

public interface RiderDetailsService {
	/**
	 * <p>
	 * This method should return the balance details of the card holder as
	 * RiderInquiryResponse.
	 * </p>
	 * <br>
	 * This method should check the card PIN from the riderInquiry request and
	 * authenticate against the user pin <br>
	 * On successful authentication this method should provide the details of card
	 * holder <br>
	 * else should respond with the RiderInquiryResponse as card pin not correct.
	 *
	 * @param riderInquiry {@link RiderInquiry} details for getting rider
	 *                       information.
	 * @return {@link RiderInquiryResponse} A rider inquiry response for the
	 *         requested {@RiderInquiry}
	 */
	RiderInquiryResponse getBalanceDetailsForRider(RiderInquiry riderInquiry);

	/**
	 * This method should return the details of all accounts <br>
	 * This is more for the audit purpose.
	 *
	 * @return
	 */
	List<Rider> getAllRiderAccountDetails();

	/**
	 * <p>
	 * This method should book a cab for rider. 1) In case of sufficient balance,
	 * cab will be booked for rider 2) In case of insufficient balance, insufficient
	 * balance message would be returned
	 * </p>
	 */
	TripDetailsResponse BookCabForRider(TripRequest bookingRequest);

	/**
	 * <p>
	 * This method should complete or cancel a cab booking/trip for rider. 
	 * 1) In case of complete trip found, complete the trip by updating the cab, trip and rider details 
	 * 2) In case of cancel trip found, cancel the trip by updating the cab, trip and rider details 
	 * 3) In case of trip not found, trip not found message would be returned
	 * </p>
	 */
	TripDetailsResponse RiderTripTransaction(BookingIdRequest bookingIdRequest, String action);

}
