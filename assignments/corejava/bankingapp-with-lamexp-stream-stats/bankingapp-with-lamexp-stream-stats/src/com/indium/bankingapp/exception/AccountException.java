package com.indium.bankingapp.exception;

public class AccountException extends Exception {
	private static final long serialVersionUID = 1L;

	public AccountException() {

	}

	public AccountException(String message) {
		super(message);
	}
}
