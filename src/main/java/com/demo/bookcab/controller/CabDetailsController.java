package com.demo.bookcab.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bookcab.dto.CabDetailsResponse;
import com.demo.bookcab.dto.CabNameRequest;
import com.demo.bookcab.dto.CabNumberRequest;
import com.demo.bookcab.entity.Cabs;
import com.demo.bookcab.service.business.cabdetails.CabDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/cab/api/cab/")
public class CabDetailsController {

	@Autowired
	CabDetailsService cabDetails;

	/**
	 * <p>
	 * This controller method will get the cab details for entered cab number
	 * 
	 * @param cabNumber {@CabNumberRequest} cab number for getting cab details
	 * @return {@link com.demo.bookcab.dto.CabDetailsResponse}. Cab details for
	 *         entered cab number.
	 */

	@Operation(summary = "Get the cab details by cab number")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with cab details for entered cab number", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CabDetailsResponse.class)) }) })
	@PostMapping("cab-cabnumber")
	public CabDetailsResponse getCabDetailsByCabNumber(@Valid @RequestBody CabNumberRequest cabNumber) {
		return this.cabDetails.getCabDetailsByCabNumber(cabNumber);
	}

	/**
	 * This controller method will get the cab details for entered cab name
	 *
	 * @param cabName {@CabNameRequest} cab name for getting cab details
	 * @return A list of @Cabs.
	 */
	@Operation(summary = "Get the cab details by cab name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with cab details for entered cab name", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cabs.class)) }) })
	@PostMapping("cab-cabname")
	public List<CabDetailsResponse> getCabDetailsByCabName(@Valid @RequestBody CabNameRequest cabName) {
		return this.cabDetails.getCabDetailsByCabName(cabName);
	}

	/**
	 * This controller method will get the cab details for entered cab name
	 *
	 *
	 * @return A list of @Cabs.
	 */
	@Operation(summary = "Get all the cab details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with all cab details", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cabs.class)) }) })
	@GetMapping("cab-all")
	public List<Cabs> getAllCabDetails() {
		return this.cabDetails.getAllCabDetails();
	}
}
