package com.flightapp.flightservice.exception.model;

public class ApiError {

	private String errorMessage;
	private String exceptionType;
	
	public ApiError() {}
	
	public ApiError(String errorMessage, String exceptionType) {
		super();
		this.errorMessage = errorMessage;
		this.exceptionType = exceptionType;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	
	
}