package com.demo.bookcab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationFootfallResponse {

	private String locationName;
	private int pickupCount;
	private int dropCount;
	private String message;
}
