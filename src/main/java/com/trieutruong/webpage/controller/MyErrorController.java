package com.trieutruong.webpage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trieutruong.webpage.model.ExceptionModel;

@RestController
public class MyErrorController implements ErrorController {

	@RequestMapping("/error")
	public ExceptionModel handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		return new ExceptionModel(statusCode.toString(),exception.getMessage());
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
