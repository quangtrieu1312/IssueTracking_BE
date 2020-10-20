package com.trieutruong.project.joblistener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Service;

import com.trieutruong.project.service.TicketService;

public class AlertTicketJobListener implements JobListener{
	private static final String LISTENER_NAME = "CronJobListener";

	private TicketService ticketService;

	public AlertTicketJobListener(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		System.out.println("JobsListener.jobToBeExecuted()");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		System.out.println("JobsListener.jobExecutionVetoed()");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		System.out.println("JobsListener.jobWasExecuted()");
	}

	public TicketService getJobService() {
		return ticketService;
	}

	@Override
	public String getName() {
		return LISTENER_NAME;
	}

}
