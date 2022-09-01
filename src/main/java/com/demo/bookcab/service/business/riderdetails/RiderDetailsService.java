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
	 * balanceInquiryResponse.
	 * </p>
	 * <br>
	 * This method should check the card PIN from the balanceInquiry request and
	 * authenticate against the user pin <br>
	 * On successful authentication this method should provide the details of card
	 * holder <br>
	 * else should respond with the BalanceInquiryResponse as card pin not correct.
	 *
	 * @param balanceInquiry {@link RiderInquiry} details for getting card
	 *                       information.
	 * @return {@link RiderInquiryResponse} A balance inquiry response for the
	 *         requested {@BalanceInquiry}
	 */
	RiderInquiryResponse getBalanceDetailsForRider(RiderInquiry balanceInquiry);

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
	 * This method should complete a cab booking/trip for rider. 1) In case of trip
	 * found, complete the trip by updating the cab, trip and rider details 2) In
	 * case of trip not found, trip not found message would be returned
	 * </p>
	 */

	TripDetailsResponse CompleteRiderTrip(BookingIdRequest bookingIdRequest);

	/**
	 * <p>
	 * This method should cancel a cab booking of rider. 1) In case of trip found,
	 * cancel the trip by updating the cab, trip and rider details 2) In case of
	 * trip not found, trip not found message would be returned
	 * </p>
	 */

	TripDetailsResponse CancelRiderTrip(BookingIdRequest bookingIdRequest);
}
