package com.nahmed.cucumber.exceptions;

@SuppressWarnings("serial")
public class FrameworkException extends RuntimeException {

	public FrameworkException(String message) {
		super(message);
	}

	public FrameworkException(String message, Throwable cause) {
		super(message, cause);
	}
}
