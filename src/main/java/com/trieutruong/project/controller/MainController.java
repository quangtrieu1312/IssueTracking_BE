package com.trieutruong.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trieutruong.project.constant.Timezones;
import com.trieutruong.project.domain.Ticket;
import com.trieutruong.project.domain.User;
import com.trieutruong.project.exception.BadInputException;
import com.trieutruong.project.model.GeneralModelResponse;
import com.trieutruong.project.model.TicketInfo;
import com.trieutruong.project.request.LoginRequest;
import com.trieutruong.project.request.SignUpRequest;
import com.trieutruong.project.request.TicketRequest;
import com.trieutruong.project.response.TicketResponse;
import com.trieutruong.project.response.TimezoneResponse;
import com.trieutruong.project.response.UserResponse;
import com.trieutruong.project.service.EmailService;
import com.trieutruong.project.service.QuartzSchedulerService;
import com.trieutruong.project.service.TicketService;
import com.trieutruong.project.service.UserService;

@RestController
public class MainController {

	@Autowired
	UserService userService;

	@Autowired
	EmailService emailService;

	@Autowired
	TicketService ticketService;

	@Autowired
	Timezones timezones;

	@Autowired
	QuartzSchedulerService quartzSchedulerService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() throws SchedulerException, IOException {
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public GeneralModelResponse login(@RequestBody LoginRequest req, HttpServletRequest request,
			HttpServletResponse response) {
		userService.login(req, request, response);
		return new GeneralModelResponse().successResponse("Successful login");
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public GeneralModelResponse signUp(@RequestBody SignUpRequest req) throws IOException, BadInputException {
		userService.signUp(req);
		return new GeneralModelResponse()
				.successResponse("Successfully signed-up, please check your email to activate account");
	}

	@RequestMapping(value = "/user/id/{userIds}", method = RequestMethod.GET)
	public UserResponse getUserByUserIds(@PathVariable(value = "userIds", required = true) List<String> userIds)
			throws BadInputException {
		List<User> users = userService.findByUserIds(userIds);
		return new UserResponse("Successfully get user info by id", users);
	}

	@RequestMapping(value = "/user/username/{usernames}", method = RequestMethod.GET)
	public UserResponse getUserByUsernames(@PathVariable(value = "usernames", required = true) List<String> usernames)
			throws BadInputException {
		List<User> users = userService.findByUsernames(usernames);
		return new UserResponse("Successfully get user info by username", users);
	}

	@RequestMapping(value = "/token/{activateToken}", method = RequestMethod.GET)
	public GeneralModelResponse activate(@PathVariable(value = "activateToken", required = true) String activateToken)
			throws BadInputException {
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
	public Page<TicketInfo> getPageAllTicket(@RequestParam(value = "searchBox", required = true) String searchBox,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "size", required = true) Integer size, Pageable pageable,
			HttpServletRequest httpRequest) throws BadInputException {
		Page<Ticket> ticketsPage = ticketService.findPageBySearchBoxAndHttpRequest(searchBox, pageable, httpRequest);
		List<Ticket> tickets = ticketsPage.getContent();
		Page<TicketInfo> ticketsInfoPage = new PageImpl<TicketInfo>(ticketService.convertTicketToTicketInfo(tickets),
				pageable, ticketsPage.getTotalElements());
		return ticketsInfoPage;
	}

	@RequestMapping(value = "/ticket/{ticketId}", method = RequestMethod.GET)
	public TicketResponse getTicket(@PathVariable(value = "ticketId") String ticketId, HttpServletRequest httpRequest)
			throws BadInputException {
		Ticket ticket = ticketService.findByTicketId(ticketId);
		User user = userService.findByHttpRequest(httpRequest);
		if (ticket.getOwnerId().equals(user.getUserId()) || ticket.getMemberIds().contains(user.getUserId())) {
			List<Ticket> tickets = new ArrayList<Ticket>();
			tickets.add(ticket);
			List<TicketInfo> ticketsInfo = ticketService.convertTicketToTicketInfo(tickets);
			return new TicketResponse("Successful get ticket", ticketsInfo);
		} else {
			throw new BadInputException("No authority to view ticket");
		}
	}

	@RequestMapping(value = "/ticket", method = RequestMethod.POST)
	public TicketResponse postTicket(@Valid @RequestBody TicketRequest request, HttpServletRequest httpRequest)
			throws BadInputException, SchedulerException, IOException {
		Ticket ticket = ticketService.create(request, httpRequest);
		if (ticket.getAlert().getMode() == true) {
			quartzSchedulerService.startJob(ticket.getTicketId());
		}
		List<Ticket> tickets = new ArrayList<Ticket>();
		tickets.add(ticket);
		List<TicketInfo> ticketsInfo = ticketService.convertTicketToTicketInfo(tickets);
		return new TicketResponse("Successful post ticket", ticketsInfo);
	}

	@RequestMapping(value = "/ticket/{ticketId}", method = RequestMethod.PUT)
	public TicketResponse putTicket(@PathVariable(value = "ticketId") String ticketId,
			@Valid @RequestBody TicketRequest request, HttpServletRequest httpRequest)
			throws BadInputException, SchedulerException, IOException {
		Ticket ticket = ticketService.update(ticketId, request, httpRequest);
		try {
			quartzSchedulerService.stopJob(ticket.getTicketId());
		} catch (Exception e) {

		}
		if (ticket.getAlert().getMode()) {
			quartzSchedulerService.startJob(ticket.getTicketId());
		}
		List<Ticket> tickets = new ArrayList<Ticket>();
		tickets.add(ticket);
		List<TicketInfo> ticketsInfo = ticketService.convertTicketToTicketInfo(tickets);
		return new TicketResponse("Successful update ticket", ticketsInfo);
	}

	@RequestMapping(value = "ticket/{ticketId}", method = RequestMethod.DELETE)
	public TicketResponse deleteTicket(@PathVariable(value = "ticketId") String ticketId,
			HttpServletRequest httpRequest) throws BadInputException {
		Ticket ticket = ticketService.delete(ticketId, httpRequest);
		try {
			quartzSchedulerService.stopJob(ticket.getTicketId());
		} catch (Exception e) {

		}
		List<Ticket> tickets = new ArrayList<Ticket>();
		tickets.add(ticket);
		List<TicketInfo> ticketsInfo = ticketService.convertTicketToTicketInfo(tickets);
		return new TicketResponse("Successful delete ticket", ticketsInfo);
	}

	@RequestMapping(value = "/timezone", method = RequestMethod.GET)
	public TimezoneResponse getTimezone() throws BadInputException {
		try {
			List<String> validTimezones = timezones.getTimezones();
			return new TimezoneResponse("Get timezones successfully", validTimezones);
		} catch (Exception e) {
			throw e;
		}
	}
}
