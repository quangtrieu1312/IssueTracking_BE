package com.trieutruong.webpage.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.service.QuartzSchedulerService;
import com.trieutruong.webpage.service.TicketService;

@Configuration
public class QuartzSchedulerConfig {
	@Autowired
	TicketService ticketService;

	@Autowired
	QuartzSchedulerService quartzSchedulerService;
	
	@PostConstruct
	public void initScript() throws Exception {
		System.out.print("Init quartz");
		List<Ticket> tickets = ticketService.findByAlertMode(Boolean.TRUE.toString());
		for (Ticket ticket : tickets) 
			quartzSchedulerService.startJob(ticket.getTicketId());
	}
}
