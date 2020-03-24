package com.trieutruong.projectzero.config;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.trieutruong.projectzero.domain.Ticket;
import com.trieutruong.projectzero.service.QuartzSchedulerService;
import com.trieutruong.projectzero.service.TicketService;

@Configuration
public class QuartzSchedulerConfig {

	@Autowired
	TicketService ticketService;

	@Autowired
	QuartzSchedulerService quartzSchedulerService;

	@PostConstruct
	public void initScript() {
		System.out.print("Init quartz");
		try {
			List<Ticket> tickets = ticketService.findByAlertMode(Boolean.TRUE);
			for (Ticket ticket : tickets)
				quartzSchedulerService.startJob(ticket.getTicketId());
		}
		catch (Exception e) {
			System.out.print("Cannot innit tickets");
		}
	}
}
