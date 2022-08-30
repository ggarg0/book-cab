package com.demo.bookcab.exceptions;

public class CabDetailsNotFoundException extends RuntimeException {
	public CabDetailsNotFoundException() {
	}

	public CabDetailsNotFoundException(String message) {
		super(message);
	}

	public CabDetailsNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
