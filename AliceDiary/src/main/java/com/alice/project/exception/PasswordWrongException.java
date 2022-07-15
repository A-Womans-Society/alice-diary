package com.alice.project.exception;

@SuppressWarnings("serial")
public class PasswordWrongException extends RuntimeException {
	public PasswordWrongException() {
		super("Password is Wrong");
	}

}
