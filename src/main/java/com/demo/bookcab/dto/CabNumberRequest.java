package com.demo.bookcab.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabNumberRequest {

	@Pattern(regexp = "^[A-Z]{3}[0-9]{1}", message = "Car number can have 3 digits with letter followed by a number")
	@NotBlank(message = "Cab number cannot be blank")
	private String cabNumber;
}
