package com.flightapp.flightservice.utility;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.flightapp.flightservice.exception.model.ApiError;
import com.flightapp.flightservice.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class GlobalCustomExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException e) {
		return new ResponseEntity<>(new ApiError(e.getMessage(), e.getClass().toString()), HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException e) {
		return new ResponseEntity<>(new ApiError(e.getMessage(), e.getClass().toString()), HttpStatus.BAD_REQUEST);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		return handleExceptionInternal(e, errors, headers, HttpStatus.BAD_REQUEST, request);
	}
	
//	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public Map<String, String> handleException(MethodArgumentNotValidException exception) {
//		Map<String, String> messages = new HashMap<>();
//		exception.getAllErrors().forEach(error -> {
//			String fieldName = ((FieldError) error).getField();
//			String errorMessage = ((FieldError) error).getDefaultMessage();
//			messages.put(fieldName, errorMessage);
//
//		});
//		return messages;
//
//	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleException(Exception e) {
		return new ResponseEntity<>(new ApiError(e.getMessage(), e.getClass().toString()),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
