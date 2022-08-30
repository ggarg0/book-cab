package com.demo.bookcab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bookcab.dto.LocationFootfallResponse;
import com.demo.bookcab.service.business.locationdetails.LocationDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/cab/api/location/")
public class LocationDetailsController {


	@Autowired
	LocationDetailsService locationDetails;

	/**
	 * This controller method will fetch the footfall details of all locations. <br>
	 * This is more of a audit feature. No authentication is added here.
	 *
	 * @return {@link com.demo.bookcab.dto.LocationFootfallResponse} A list of
	 *         Location Footfall Details.
	 */
	@Operation(summary = "Get all locations footfall count")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with  footfall count of all locations", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = LocationFootfallResponse.class)) }) })
	@GetMapping("location-footfall")
	public List<LocationFootfallResponse> getAllLocationFootfall() {
		return this.locationDetails.getAllLocationFootfall();
	}



}
