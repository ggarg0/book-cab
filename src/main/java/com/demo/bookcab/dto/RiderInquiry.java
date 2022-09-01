package com.demo.bookcab.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderInquiry {

	@Pattern(regexp = "^[0-9]{10}", message = "User name can only be mobile number and that to 10 digits only")
	@NotBlank(message = "User name cannot be blank")
	private String userName;

	@Pattern(regexp = "^[0-9]{4}", message = "Wallet pin can only be number and that to 4 digits only")
	@NotBlank(message = "Wallet pin cannot be blank")
	private String walletPin;

	@Override
	public String toString() {
		return "RiderInquiry{" + " userName='" + userName + ", walletPin= *****" + '\'' + '}';
	}
}
