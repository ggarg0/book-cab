package com.demo.bookcab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabDetailsResponse {

	private String cabNumber;
	private String cabName;
	private String driverName;
	private Double rate;
	private String status;
	private String message;

}
