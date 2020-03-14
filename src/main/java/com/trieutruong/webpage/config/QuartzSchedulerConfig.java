package com.trieutruong.webpage.config;

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

import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.service.QuartzSchedulerService;
import com.trieutruong.webpage.service.TicketService;

@Configuration
public class QuartzSchedulerConfig {
	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {        
	    SchedulerFactoryBean scheduler = new SchedulerFactoryBean();  
	    scheduler.setQuartzProperties(quartzProperties());
	    return scheduler;
	}

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
