package com.trieutruong.projectzero.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.quartz.SchedulerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

import com.trieutruong.projectzero.exception.BadInputException;
import com.trieutruong.projectzero.model.ExceptionModel;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler{
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

	@ExceptionHandler(SchedulerException.class)
	public ResponseEntity<ExceptionModel> handleSchedulerExceptionException(HttpServletRequest request, Exception ex) {
		ExceptionModel response = new ExceptionModel("Cannot schedule ticket", ex.getMessage());
		return new ResponseEntity<ExceptionModel>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	 @Override
	    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                                                                  HttpHeaders headers,
	                                                                  HttpStatus status, WebRequest request) {

	        Map<String, Object> body = new LinkedHashMap<>();
	        body.put("status", "false");
	        body.put("error", "Invalid input");
	        //Get all errors
	        List<String> msg = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(x -> x.getDefaultMessage())
	                .collect(Collectors.toList());

	        body.put("msg", msg);

	        return new ResponseEntity<>(body, headers, status);

	    }
}
