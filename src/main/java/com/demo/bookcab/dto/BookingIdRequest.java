package com.demo.bookcab.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingIdRequest {

	@Pattern(regexp = "^[0-9]{10}", message = "Username can only be number and that to 10 digits only")
	@NotBlank(message = "Username cannot be blank")
	private String userName;

	@Pattern(regexp = "^[0-9]{4}", message = "Pin can only be number and that to 4 digits only")
	@NotBlank(message = "PIN cannot be blank")
	private String walletPin;
	
	private long bookingId;
	
	@Override
	public String toString() {
		return "BookingIdRequest{" + " userName='" + userName + ", walletPin= *****" + '\'' + '}';
	}
}
