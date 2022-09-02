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
import com.demo.bookcab.dto.TripDetailsResponse;
import com.demo.bookcab.entity.Trips;
import com.demo.bookcab.service.business.tripdetails.TripDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/cab/api/trip/")
public class TripDetailsController {

	@Autowired
	TripDetailsService tripDetails;
	
	/**
	 * This controller method will get the trip details for entered user name
	 *
	 * @param bookingIdRequest {BookingIdRequest} Custom request object for getting trip details
	 * @return A list of TripDetailsResponse.
	 */
	@Operation(summary = "Get the trip details by username")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with trip details for entered user name", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TripDetailsResponse.class)) }) })
	@PostMapping("trip-username")
	public List<TripDetailsResponse> getTripDetailsByUserName(@Valid @RequestBody RiderInquiry bookingIdRequest) {
		return this.tripDetails.getTripDetailsByUserName(bookingIdRequest);
	}
	

	/**
	 * This controller method will get all the trips details
	 *
	 *
	 * @return A list of @Trips.
	 */
	@Operation(summary = "Get all the trips details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with all trips details", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Trips.class)) }) })
	@GetMapping("trips-all")
	public List<Trips> getAllTripsDetails() {
		return this.tripDetails.getAllTripsDetails();
	}

}
