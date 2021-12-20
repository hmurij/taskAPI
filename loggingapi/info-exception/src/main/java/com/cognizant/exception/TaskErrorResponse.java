package com.cognizant.exception;

import java.util.Date;

public class TaskErrorResponse {

	private int httpCode;
	private String message;
	private Date date;

	public TaskErrorResponse() {
	}

	public TaskErrorResponse(int httpCode, String message, Date date) {
		super();
		this.httpCode = httpCode;
		this.message = message;
		this.date = date;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
