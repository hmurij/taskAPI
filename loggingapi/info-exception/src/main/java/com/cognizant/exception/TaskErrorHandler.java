package com.cognizant.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskErrorHandler {

	@ExceptionHandler
	public ResponseEntity<TaskErrorResponse> handleTaskNotFoundException(TaskNotFoundException e) {
		return new ResponseEntity<TaskErrorResponse>(
				new TaskErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), new Date()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<TaskErrorResponse> handleExceptions(Exception e) {
		return new ResponseEntity<TaskErrorResponse>(
				new TaskErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), new Date()),
				HttpStatus.BAD_REQUEST);
	}

}
