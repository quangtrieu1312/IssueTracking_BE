package com.trieutruong.webpage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

import com.trieutruong.webpage.exception.BadInputException;
import com.trieutruong.webpage.model.ExceptionModel;

@ControllerAdvice
public class GlobalExceptionController {
	@ExceptionHandler(BadInputException.class)
	public ResponseEntity<ExceptionModel> handleBadInputException(HttpServletRequest request, Exception ex) {
		ExceptionModel response = new ExceptionModel("Bad input", ex.getMessage());
		return new ResponseEntity<ExceptionModel>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionModel> handleBadCredentialsException(HttpServletRequest request, Exception ex) {
		ExceptionModel response = new ExceptionModel("Wrong username/password", ex.getMessage());
		return new ResponseEntity<ExceptionModel>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ExceptionModel> handleForbiddenException(HttpServletRequest request, Exception ex) {
		ExceptionModel response = new ExceptionModel("No authorization to access page", ex.getMessage());
		return new ResponseEntity<ExceptionModel>(response, HttpStatus.FORBIDDEN);
	}
}
