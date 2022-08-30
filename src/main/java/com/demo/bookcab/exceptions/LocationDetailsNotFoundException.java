package com.demo.bookcab.exceptions;

public class LocationDetailsNotFoundException extends RuntimeException {
	public LocationDetailsNotFoundException() {
	}

	public LocationDetailsNotFoundException(String message) {
		super(message);
	}

	public LocationDetailsNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
