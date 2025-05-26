package com.nahmed.cucumber.exceptions;

@SuppressWarnings("serial")
public class InvalidPathForExcelException extends FrameworkException {

	public InvalidPathForExcelException(String message) {
		super(message);
	}

	public InvalidPathForExcelException(String message, Throwable cause) {
		super(message, cause);
	}

}
