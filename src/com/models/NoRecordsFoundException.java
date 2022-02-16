package com.models;

public class NoRecordsFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	public NoRecordsFoundException(String message) {
		super(message);
	}
}