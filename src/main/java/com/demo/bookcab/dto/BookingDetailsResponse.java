package com.demo.bookcab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsResponse {
	
	private String userName;
	private String cabName;
	private String cabNumber;
	private String driverName;
	private String pickUp;
	private String drop;
	private Double distance;
	private Double rate;
	private Double fare;
	private Double walletBalance;
	private Double walletBalanceOnHold;
	private String status;
	private Long bookingId;
	private String message;
}
