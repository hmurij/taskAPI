package com.cognizant.exception;

@SuppressWarnings("serial")
public class TaskNotFoundException extends RuntimeException {

	public TaskNotFoundException(String message) {
		super(message);
	}

}
