package com.demo.bookcab.exceptions;

public class TripDetailsNotFoundException extends RuntimeException {
	public TripDetailsNotFoundException() {
	}

	public TripDetailsNotFoundException(String message) {
		super(message);
	}

	public TripDetailsNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
