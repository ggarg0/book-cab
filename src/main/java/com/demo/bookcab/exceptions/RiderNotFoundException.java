package com.demo.bookcab.exceptions;

public class RiderNotFoundException extends RuntimeException {
	public RiderNotFoundException() {
	}

	public RiderNotFoundException(String message) {
		super(message);
	}

	public RiderNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
