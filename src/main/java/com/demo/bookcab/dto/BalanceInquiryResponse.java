package com.demo.bookcab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceInquiryResponse {
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private Double walletBalance;
	private Double walletBalanceOnhold;
	private String message;
}
