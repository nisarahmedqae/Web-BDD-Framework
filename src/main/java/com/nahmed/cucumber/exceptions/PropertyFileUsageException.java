package com.nahmed.cucumber.exceptions;

@SuppressWarnings("serial")
public class PropertyFileUsageException extends FrameworkException {

	public PropertyFileUsageException(String message) {
		super(message);
	}

	public PropertyFileUsageException(String message, Throwable cause) {
		super(message, cause);
	}

}
