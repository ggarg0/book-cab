package com.demo.bookcab.service.business.riderdetails;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.bookcab.dto.BalanceInquiry;
import com.demo.bookcab.dto.BalanceInquiryResponse;
import com.demo.bookcab.dto.BookingDetailsResponse;
import com.demo.bookcab.dto.BookingRequest;
import com.demo.bookcab.entity.Rider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
	 * @param balanceInquiry {@link BalanceInquiry} details for getting card
	 *                       information.
	 * @return {@link BalanceInquiryResponse} A balance inquiry response for the
	 *         requested {@BalanceInquiry}
	 */
	BalanceInquiryResponse getBalanceDetailsForRider(BalanceInquiry balanceInquiry);

	/**
	 * This method should return the details of all accounts <br>
	 * This is more for the audit purpose.
	 *
	 * @return
	 */
	List<Rider> getAllRiderAccountDetails();

	/**
	 * <p>
	 * This method should save the account details.
	 * </p>
	 * <br>
	 * 
	 * @param {List of @link Rider}
	 *
	 */
	void saveAllRiderAccountDetails(List<Rider> riderDetails);

	/**
	 * <p>
	 * This controller method will authenticate the pin for user, On successful
	 * validation, tariff for the journey will be calculated. 1) In case of
	 * sufficient balance, cab will be booked for rider 2) In case of insufficient
	 * balance, insufficient balance message would be returned
	 * </p>
	 *
	 * @param balanceInquiry {@link com.demo.bookcab.dto.BookingRequest} A custom
	 *                       trip details object.
	 * @return {@link com.demo.bookcab.dto.BookingDetailsResponse}. Trip details
	 *         response for rider.
	 */

	BookingDetailsResponse BookCabForRider(BookingRequest bookingRequest);

}
