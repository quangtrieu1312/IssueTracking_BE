package com.trieutruong.webpage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.authentication.BadCredentialsException;

import com.trieutruong.webpage.exception.BadInputException;
import com.trieutruong.webpage.model.ExceptionModel;

@ControllerAdvice
public class GlobalExceptionController {
	@ExceptionHandler(BadInputException.class)
	public ResponseEntity<ExceptionModel> handleBadInputException(HttpServletRequest request, Exception ex) {
		ExceptionModel response = new ExceptionModel(ex.getMessage(), "Bad input");
		return new ResponseEntity<ExceptionModel>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionModel> handleBadCredentialsException(HttpServletRequest request, Exception ex) {
		ExceptionModel response = new ExceptionModel(ex.getMessage(), "Wrong username/password");
		return new ResponseEntity<ExceptionModel>(response, HttpStatus.UNAUTHORIZED);
	}
}
