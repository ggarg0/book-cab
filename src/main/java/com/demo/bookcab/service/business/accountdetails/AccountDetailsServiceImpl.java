package com.demo.bookcab.service.business.accountdetails;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookcab.constant.MessageConstants;
import com.demo.bookcab.data.service.AccountDetailsDataService;
import com.demo.bookcab.dto.BalanceInquiry;
import com.demo.bookcab.dto.BalanceInquiryResponse;
import com.demo.bookcab.entity.UserAccount;
import com.demo.bookcab.exceptions.UserAccountNotFoundException;
import com.demo.bookcab.security.AuthenticationService;

import lombok.Data;

@Service
@Data
public class AccountDetailsServiceImpl implements AccountDetailsService {

	@Autowired
	private AccountDetailsDataService accountDetailsDataService;

	@Autowired
	AuthenticationService authenticationService;

	/**
	 * {@inheritDoc}
	 *
	 * @param balanceInquiry {@link BalanceInquiry} details for getting account
	 *                       information.
	 * @return
	 */
	public BalanceInquiryResponse getBalanceDetailsForUser(BalanceInquiry balanceInquiry) {
		if (Objects.isNull(balanceInquiry)) {
			return new BalanceInquiryResponse(null, null, null, null, 0.0, 0.0, MessageConstants.InvalidBalanceInquiry);
		}

		// Fetch card details from DB.
		UserAccount userAccount = null;
		try {
			userAccount = accountDetailsDataService.getUserAccountDetails(balanceInquiry.getUserName());

		} catch (UserAccountNotFoundException exp) {
			return new BalanceInquiryResponse(balanceInquiry.getUserName(), null, null, null, 0.0, 0.0,
					MessageConstants.UserNameNotFound);
		}

		// Authenticate
		if (this.authenticationService.authenticateUserAccount(userAccount, balanceInquiry.getWalletPin())) {
			// Respond Balance.
			return new BalanceInquiryResponse(userAccount.getUser_name(), userAccount.getFirst_name(),
					userAccount.getLast_name(), userAccount.getEmail(), userAccount.getWallet_balance(),
					userAccount.getWallet_balance_onhold(), "");

		} else {
			return new BalanceInquiryResponse(balanceInquiry.getUserName(), null, null, null, 0.0, 0.0,
					MessageConstants.InvalidPin);
		}

	}
	
	@Override
	public List<UserAccount> getAllAccountDetails() {
		return this.accountDetailsDataService.getAllAccountDetails();
	}
	@Override
	public void saveAllAccountDetails(List<UserAccount> accountDetails) {
		this.accountDetailsDataService.saveAllAccountDetails(accountDetails);
	}

}
