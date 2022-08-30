package com.demo.bookcab.service.business.accountdetails;

import java.util.List;

import com.demo.bookcab.dto.BalanceInquiry;
import com.demo.bookcab.dto.BalanceInquiryResponse;
import com.demo.bookcab.entity.UserAccount;

public interface AccountDetailsService {
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
	BalanceInquiryResponse getBalanceDetailsForUser(BalanceInquiry balanceInquiry);

	/**
	 * This method should return the details of all accounts <br>
	 * This is more for the audit purpose.
	 *
	 * @return
	 */
	List<UserAccount> getAllAccountDetails();

	/**
	 * <p>
	 * This method should save the account details.
	 * </p>
	 * <br>
	 * 
	 * @param {List of @link MetroCard}
	 *
	 */
	void saveAllAccountDetails(List<UserAccount> accountDetails);
	
}
