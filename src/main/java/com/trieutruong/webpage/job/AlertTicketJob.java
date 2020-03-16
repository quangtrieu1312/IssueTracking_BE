package com.trieutruong.webpage.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.service.EmailService;
import com.trieutruong.webpage.service.TicketService;

public class AlertTicketJob implements Job {

	@Autowired
	TicketService ticketService;

	@Autowired
	EmailService emailService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			String ticketId = new String();
			ticketId = context.getJobDetail().getKey().getName();
			Ticket ticket = ticketService.findByTicketId(ticketId);
			for (String email : ticket.getEmails()) {
				emailService.send("no-reply@trieutruong.com", email, "Ticket alert",
						"Ticket name: " + ticket.getName() + "\nTicket description: " + ticket.getDescription());
			}
		} catch (Exception e) {
			throw new JobExecutionException(
					"Can not execute job with job name " + context.getJobDetail().getKey().getName() + " in job group "
							+ context.getJobDetail().getKey().getGroup());
		}
	}
}
