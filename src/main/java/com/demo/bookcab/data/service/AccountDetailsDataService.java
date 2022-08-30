package com.demo.bookcab.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bookcab.entity.UserAccount;
import com.demo.bookcab.repository.AccountDetailsRepository;

@Service
public class AccountDetailsDataService {

	@Autowired
	private AccountDetailsRepository accountDetailsRepository;

	/**
	 * @param userName
	 * @return Account holder if account exists, else returns null
	 */
	public UserAccount getUserAccountDetails(String userName) {
		UserAccount cardHolderDetails = this.accountDetailsRepository.getUserAccountDetails(userName);
		return cardHolderDetails;
	}

	public List<UserAccount> getAllAccountDetails() {
		List<UserAccount> accountDetails = new ArrayList<>();
		this.accountDetailsRepository.findAll().forEach(account -> {
			account.setWallet_pin(null);
			accountDetails.add(account);
		});
		return accountDetails;
	}

	public void saveAllAccountDetails(List<UserAccount> accountDetails) {
		this.accountDetailsRepository.saveAll(accountDetails);
	}

}
