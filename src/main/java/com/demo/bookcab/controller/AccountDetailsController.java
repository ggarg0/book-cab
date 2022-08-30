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
import com.demo.bookcab.entity.UserAccount;
import com.demo.bookcab.service.business.accountdetails.AccountDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/cab/api/accounts/")
public class AccountDetailsController {

	@Autowired
	AccountDetailsService accountDetails;

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
}
