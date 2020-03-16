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
//		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		if (statusCode == null) {
			statusCode = 500;
		}
//		if (exception == null) {
//			exception = new Exception();
//		}
		return new ExceptionModel(statusCode.toString(), "Unexpected error, please report to admin");
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
