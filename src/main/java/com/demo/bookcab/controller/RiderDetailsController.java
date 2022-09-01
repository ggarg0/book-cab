package com.demo.bookcab.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bookcab.dto.RiderInquiry;
import com.demo.bookcab.dto.RiderInquiryResponse;
import com.demo.bookcab.dto.BookingIdRequest;
import com.demo.bookcab.dto.TripDetailsResponse;
import com.demo.bookcab.dto.TripRequest;
import com.demo.bookcab.entity.Rider;
import com.demo.bookcab.service.business.riderdetails.RiderDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/cab/api/rider/")
public class RiderDetailsController {

	@Autowired
	RiderDetailsService riderDetails;

	/**
	 * <p>
	 * This controller method will authenticate the wallet pin for user, On
	 * successful validation, The requested users balance amount will be returned.
	 * </p>
	 *
	 * @param balanceInquiry {@link com.demo.bookcab.dto.RiderInquiry} A custom
	 *                       balance inquiry object.
	 * @return {@link com.demo.bookcab.dto.RiderInquiryResponse}. Account details
	 *         for user.
	 */

	@Operation(summary = "Get the wallet balance for rider")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with account details for rider", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RiderInquiryResponse.class)) }) })
	@PostMapping("balance")
	public RiderInquiryResponse getBalanceDetailsForRider(@Valid @RequestBody RiderInquiry balanceInquiry) {
		return this.riderDetails.getBalanceDetailsForRider(balanceInquiry);
	}

	/**
	 * This controller method will fetch the details of all account holders. <br>
	 * This is more of a audit feature. No authentication is added here.
	 *
	 * @return {@link com.demo.bookcab.entity.Rider} A list of account holder
	 *         details.
	 */

	@Operation(summary = "Get all the rider account holder details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with the rider account holder details", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Rider.class)) }) })
	@GetMapping("account-all")
	public List<Rider> getAllRiderAccountDetails() {
		return this.riderDetails.getAllRiderAccountDetails();
	}

	/**
	 * <p>
	 * This controller method will authenticate the pin for user, On successful
	 * validation, tariff for the journey will be calculated. 1) In case of
	 * sufficient balance, cab will be booked for rider 2) In case of insufficient
	 * balance, insufficient balance message would be returned
	 * </p>
	 *
	 * @param balanceInquiry {@link com.demo.bookcab.dto.TripRequest} A custom trip
	 *                       details object.
	 * @return {@link com.demo.bookcab.dto.TripDetailsResponse}. Trip details
	 *         response for rider.
	 */

	@Operation(summary = "Book a cab and get trip details for rider")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with cab booking details for trip", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TripDetailsResponse.class)) }) })
	@PostMapping("book-trip")
	public TripDetailsResponse BookCabForRider(@Valid @RequestBody TripRequest tripRequest) {
		return this.riderDetails.BookCabForRider(tripRequest);
	}

	/**
	 * <p>
	 * This controller method will authenticate the pin for user, On successful
	 * validation, trip details will be searched using bookingId. 1) In case of trip
	 * found, complete the trip by updating the cab, trip and rider details 2) In
	 * case of trip not found, trip not found message would be returned
	 * </p>
	 *
	 * @param balanceInquiry {@link com.demo.bookcab.dto.TripRequest} A custom trip
	 *                       details object.
	 * @return {@link com.demo.bookcab.dto.TripDetailsResponse}. Trip details
	 *         response for rider.
	 */

	@Operation(summary = "Complete a cab booking and get trip details for rider")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with cab booking details after completing a trip", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TripDetailsResponse.class)) }) })
	@PostMapping("complete-trip")
	public TripDetailsResponse CompleteRiderTrip(@Valid @RequestBody BookingIdRequest bookingIdRequest) {
		return this.riderDetails.CompleteRiderTrip(bookingIdRequest);
	}

	/**
	 * <p>
	 * This controller method will authenticate the pin for user, On successful
	 * validation, trip details will be searched using bookingId. 1) In case of trip
	 * found, cancel the trip by updating the cab, trip and rider details 2) In case
	 * of trip not found, trip not found message would be returned
	 * </p>
	 *
	 * @param balanceInquiry {@link com.demo.bookcab.dto.TripRequest} A custom trip
	 *                       details object.
	 * @return {@link com.demo.bookcab.dto.TripDetailsResponse}. Trip details
	 *         response for rider.
	 */

	@Operation(summary = "Cancel a cab booking and get refund details for rider")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with cab booking details after cancelling a trip", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TripDetailsResponse.class)) }) })
	@PostMapping("cancel-trip")
	public TripDetailsResponse CancelRiderTrip(@Valid @RequestBody BookingIdRequest bookingIdRequest) {
		return this.riderDetails.CancelRiderTrip(bookingIdRequest);
	}
}
