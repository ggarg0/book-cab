package com.demo.bookcab.exceptions;

public class InsufficientFundsException extends RuntimeException {
	public InsufficientFundsException() {
	}

	public InsufficientFundsException(String message) {
		super(message);
	}

	public InsufficientFundsException(String message, Throwable throwable) {
		super(message, throwable);
	}
}