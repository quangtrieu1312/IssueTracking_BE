package com.trieutruong.projectzero.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trieutruong.projectzero.model.ExceptionModel;

@RestController
public class MyErrorController implements ErrorController {

	@RequestMapping("/error")
	public ExceptionModel handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

		if (statusCode == null || statusCode == 500) {
			statusCode = 500;
			return new ExceptionModel(statusCode.toString(), "Unexpected error, please report to admin");
		} else {
			Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
			return new ExceptionModel(statusCode.toString(), exception.getMessage());
		}

	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
