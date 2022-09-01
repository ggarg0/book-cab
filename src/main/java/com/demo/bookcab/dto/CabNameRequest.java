package com.demo.bookcab.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CabNameRequest {

	@NotBlank(message = "Cab name cannot be blank")
	private String cabName;
}
