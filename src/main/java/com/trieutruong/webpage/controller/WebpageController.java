package com.trieutruong.webpage.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.trieutruong.webpage.exception.BadInputException;
import com.trieutruong.webpage.request.ActivateRequest;
import com.trieutruong.webpage.request.EmailRequest;
import com.trieutruong.webpage.request.LoginRequest;
import com.trieutruong.webpage.request.SignUpRequest;
import com.trieutruong.webpage.service.EmailService;
import com.trieutruong.webpage.service.UserService;

@Controller
public class WebpageController {

	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage() {
		return "home.html";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login.html";
	}	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute LoginRequest req, HttpServletRequest request, HttpServletResponse response) {
		userService.login(req, request, response);
		return "loginSuccess.html";
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String signUpPage() {
		return "signUp.html";
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String signUp(@ModelAttribute SignUpRequest req) throws IOException {
		userService.signUp(req);
		return "home.html";
	}
	
	@RequestMapping(value = "/testUser", method = RequestMethod.GET)
	public String testUserPage() {
		return "testUser.html";
	}
	
	@RequestMapping(value = "/testAdmin", method = RequestMethod.GET)
	public String testAdminPage() {
		return "testAdmin.html";
	}
	
	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public String activate(@RequestParam String activateToken) throws BadInputException {
		userService.activate(activateToken);
		return "activateSuccess.html";
	}
	
	@RequestMapping(value = "/sendActivateToken", method = RequestMethod.GET)
	public String sendActivateTokenPage(@ModelAttribute ActivateRequest req) throws BadInputException, IOException {
		return "sendActivateToken.html";
	}
	
	@RequestMapping(value = "/sendActivateToken", method = RequestMethod.POST)
	public String sendActivateToken(@ModelAttribute ActivateRequest req) throws BadInputException, IOException {
		userService.sendActivateToken(req);
		return "sendEmailSuccess.html";
	}
	
	@RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
	public String sendEmailPage() {
		return "sendEmail.html";
	}
	
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public String sendEmail(@ModelAttribute EmailRequest req) throws IOException {
		emailService.sendEmail(req);
		return "sendEmailSuccess.html";
	}
	
//	@RequestMapping(value = "/schedule", method = RequestMethod.GET)
//	public String getSchedulePage() {
//		
//	}
}
