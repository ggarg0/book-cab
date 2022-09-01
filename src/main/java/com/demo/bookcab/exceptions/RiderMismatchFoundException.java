package com.demo.bookcab.exceptions;

public class RiderMismatchFoundException extends RuntimeException {
	public RiderMismatchFoundException() {
	}

	public RiderMismatchFoundException(String message) {
		super(message);
	}

	public RiderMismatchFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
