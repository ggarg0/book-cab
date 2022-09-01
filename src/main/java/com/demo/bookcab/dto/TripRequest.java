package com.demo.bookcab.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRequest {
	@Pattern(regexp = "^[0-9]{10}", message = "Username can only be number and that to 10 digits only")
	@NotBlank(message = "Username cannot be blank")
	private String userName;

	@Pattern(regexp = "^[0-9]{4}", message = "Pin can only be number and that to 4 digits only")
	@NotBlank(message = "PIN cannot be blank")
	private String walletPin;
	
	@NotBlank(message = "Cab name cannot be blank")
	private String cabName;

	@Pattern(regexp = "^[A-Z]{1}[0-9]{1}", message = "Station can have 2 digits with letter followed by a number")
	@NotBlank(message = "Pickup cannot be blank")
	private String pickUp;

	@Pattern(regexp = "^[A-Z]{1}[0-9]{1}", message = "Station can have 2 digits with letter followed by a number")
	@NotBlank(message = "Drop cannot be blank")
	private String drop;

}
