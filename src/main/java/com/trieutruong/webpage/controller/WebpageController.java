package com.trieutruong.webpage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.exception.BadInputException;
import com.trieutruong.webpage.model.GeneralModelResponse;
import com.trieutruong.webpage.request.LoginRequest;
import com.trieutruong.webpage.request.TicketRequest;
import com.trieutruong.webpage.response.TicketResponse;
import com.trieutruong.webpage.request.SignUpRequest;
import com.trieutruong.webpage.service.EmailService;
import com.trieutruong.webpage.service.QuartzSchedulerService;
import com.trieutruong.webpage.service.TicketService;
import com.trieutruong.webpage.service.UserService;

@RestController
public class WebpageController {

	@Autowired
	UserService userService;

	@Autowired
	EmailService emailService;

	@Autowired
	TicketService ticketService;
	
	@Autowired
	QuartzSchedulerService quartzSchedulerService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage() throws SchedulerException, IOException {
	/*	List<Ticket> tickets = ticketService.findByAlertMode(Boolean.TRUE.toString());
		for (Ticket ticket : tickets) 
			quartzSchedulerService.startJob(ticket.getTicketId());*/
		return "Home page";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public GeneralModelResponse login(@RequestBody LoginRequest req, HttpServletRequest request,
			HttpServletResponse response) {
		userService.login(req, request, response);
		return new GeneralModelResponse().successResponse("Successful login");
	}
	
	@RequestMapping(value = "/testUser", method = RequestMethod.GET)
	public String testUserPage() {
		return "testUser";
	}

	@RequestMapping(value = "/testAdmin", method = RequestMethod.GET)
	public String testAdminPage() {
		return "testAdmin";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public GeneralModelResponse signUp(@RequestBody SignUpRequest req) throws IOException {
		userService.signUp(req);
		return new GeneralModelResponse().successResponse("Successful signup");
	}

	@RequestMapping(value = "/token", method = RequestMethod.PUT)
	public GeneralModelResponse activate(@RequestParam String activateToken) throws BadInputException {
		userService.activateByToken(activateToken);
		return new GeneralModelResponse().successResponse("Successful activate");
	}

	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public GeneralModelResponse resendActivateToken(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "email", required = true) String email) throws BadInputException, IOException {
		userService.sendActivateToken(username, email);
		return new GeneralModelResponse().successResponse("Send activate token successfully");
	}

	@RequestMapping(value = "/ticket", method = RequestMethod.GET)
	public TicketResponse getTicket(HttpServletRequest httpRequest) throws BadInputException {
		List<Ticket> tickets = ticketService.findByHttpRequest(httpRequest);
		return new TicketResponse("Successful get tickets", tickets);
	}

	@RequestMapping(value = "/ticket", method = RequestMethod.POST)
	public TicketResponse postTicket(@RequestBody TicketRequest request, HttpServletRequest httpRequest)
			throws BadInputException {
		Ticket ticket = ticketService.create(request, httpRequest);
		List<Ticket> tickets = new ArrayList<Ticket>();
		tickets.add(ticket);
		return new TicketResponse("Successful post ticket", tickets);
	}

	@RequestMapping(value = "/ticket/{ticketId}", method = RequestMethod.PUT)
	public TicketResponse putTicket(@PathVariable(value = "ticketId") String ticketId,
			@RequestBody TicketRequest request, HttpServletRequest httpRequest) throws BadInputException {
		Ticket ticket = ticketService.update(ticketId, request, httpRequest);
		List<Ticket> tickets = new ArrayList<Ticket>();
		tickets.add(ticket);
		return new TicketResponse("Successful update ticket", tickets);
	}
}
