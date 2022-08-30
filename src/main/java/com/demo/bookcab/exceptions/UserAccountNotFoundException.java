package com.demo.bookcab.exceptions;

public class UserAccountNotFoundException extends RuntimeException {
	public UserAccountNotFoundException() {
	}

	public UserAccountNotFoundException(String message) {
		super(message);
	}

	public UserAccountNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
