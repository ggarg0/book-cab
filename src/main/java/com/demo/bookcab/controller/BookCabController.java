package com.demo.bookcab.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bookcab.dto.BalanceInquiry;
import com.demo.bookcab.dto.BalanceInquiryResponse;
import com.demo.bookcab.dto.CabDetailsResponse;
import com.demo.bookcab.dto.LocationFootfallResponse;
import com.demo.bookcab.entity.Cabs;
import com.demo.bookcab.entity.UserAccount;
import com.demo.bookcab.service.business.accountdetails.AccountDetailsService;
import com.demo.bookcab.service.business.cabdetails.CabDetailsService;
import com.demo.bookcab.service.business.locationdetails.LocationDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/cab/api")
public class BookCabController {

	@Autowired
	AccountDetailsService accountDetails;

	@Autowired
	LocationDetailsService locationDetails;

	@Autowired
	CabDetailsService cabDetails;

	/**
	 * <p>
	 * This controller method will authenticate the wallet pin for user, On
	 * successful validation, The requested users balance amount will be returned.
	 * </p>
	 *
	 * @param balanceInquiry {@link com.demo.bookcab.dto.BalanceInquiry} A custom
	 *                       balance inquiry object.
	 * @return {@link com.demo.bookcab.dto.BalanceInquiryResponse}. Account details
	 *         for user.
	 */

	@Operation(summary = "Get the wallet balance for user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with account details for user", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = BalanceInquiryResponse.class)) }) })
	@PostMapping("balance")
	public BalanceInquiryResponse getBalanceDetailsForUser(@Valid @RequestBody BalanceInquiry balanceInquiry) {
		return this.accountDetails.getBalanceDetailsForUser(balanceInquiry);
	}

	/**
	 * This controller method will fetch the details of all account holders. <br>
	 * This is more of a audit feature. No authentication is added here.
	 *
	 * @return {@link com.demo.bookcab.entity.UserAccount} A list of account holder
	 *         details.
	 */

	@Operation(summary = "Get all the account holder details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with the account holder details", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserAccount.class)) }) })
	@GetMapping("account-all")
	public List<UserAccount> getAllAccountDetails() {
		return this.accountDetails.getAllAccountDetails();
	}

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

	/**
	 * <p>
	 * This controller method will get the cab details for entered cab number
	 * 
	 * @param cabNumber {String} cab number for getting cab details
	 * @return {@link com.demo.bookcab.dto.CabDetailsResponse}. Cab details for
	 *         entered cab number.
	 */

	@Operation(summary = "Get the cab details by cab number")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with cab details for entered cab number", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CabDetailsResponse.class)) }) })
	@PostMapping("cab-cabnumber")
	public CabDetailsResponse getCabDetailsByCabNumber(@RequestBody String cabNumber) {
		return this.cabDetails.getCabDetailsByCabNumber(cabNumber);
	}

	/**
	 * This controller method will get the cab details for entered cab name
	 *
	 * @param cabName {String} cab name for getting cab details
	 * @return A list of @Cabs.
	 */
	@Operation(summary = "Get the cab details by cab name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Will respond with cab details for entered cab name", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cabs.class)) }) })
	@PostMapping("cab-cabname")
	public List<Cabs> getCabDetailsByCabName(@RequestBody String cabName) {
		return this.cabDetails.getCabDetailsByCabName(cabName);
	}

	/**
	 * This controller method will get the cab details for entered cab name
	 *
	 * @param cabName {String} cab name for getting cab details
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
