package com.trieutruong.project.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.trieutruong.project.domain.Ticket;
import com.trieutruong.project.domain.User;
import com.trieutruong.project.exception.BadInputException;
import com.trieutruong.project.service.EmailService;
import com.trieutruong.project.service.TicketService;
import com.trieutruong.project.service.UserService;

public class AlertTicketJob implements Job {

	@Autowired
	TicketService ticketService;

	@Autowired
	EmailService emailService;

	@Autowired
	UserService userService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			String ticketId = new String();
			ticketId = context.getJobDetail().getKey().getName();
			Ticket ticket = ticketService.findByTicketId(ticketId);
			sendEmailToOwner(ticket);
			sendEmailToMembers(ticket);
			sendEmailToExtraEmails(ticket.getEmails(),ticket);
		} catch (Exception e) {
			throw new JobExecutionException(
					"Can not execute job with job name " + context.getJobDetail().getKey().getName() + " in job group "
							+ context.getJobDetail().getKey().getGroup());
		}
	}

	private void sendEmailToOwner(Ticket ticket) throws BadInputException, IOException {
		User owner = userService.findByUserId(ticket.getOwnerId());
		sendEmailToUser(owner, ticket);
	}

	private void sendEmailToMembers(Ticket ticket) throws BadInputException, IOException {
		List<User> members = userService.findByUserIds(ticket.getMemberIds());
		for (User member : members) {
			sendEmailToUser(member, ticket);
		}
	}

	private void sendEmailToExtraEmails(List<String> emails, Ticket ticket) throws IOException {
		for (String email : emails) {
			sendEmailToAddress(email, ticket);
		}
	}

	private void sendEmailToUser(User user, Ticket ticket) throws IOException {
		emailService.send("no-reply@trieutruong.com", user.getEmail(), "Ticket alert",
				"Ticket name: " + ticket.getName() + "\nTicket description: " + ticket.getDescription());
	}

	private void sendEmailToAddress(String email, Ticket ticket) throws IOException {
		emailService.send("no-reply@trieutruong.com", email, "Ticket alert",
				"Ticket name: " + ticket.getName() + "\nTicket description: " + ticket.getDescription());
	}

}
