package com.trieutruong.webpage.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.exception.BadInputException;
import com.trieutruong.webpage.job.AlertTicketJob;
import com.trieutruong.webpage.joblistener.AlertTicketJobListener;
import com.trieutruong.webpage.repository.TicketRepository;
import com.trieutruong.webpage.service.QuartzSchedulerService;
import com.trieutruong.webpage.service.TicketService;

@Service
public class QuartzSchedulerServiceImpl implements QuartzSchedulerService {

	@Autowired
	TicketService ticketService;

	@Autowired
	Scheduler scheduler;

	@Override
	public void startJob(String ticketId) throws SchedulerException, IOException, BadInputException {

		Ticket ticket = ticketService.findByTicketId(ticketId);
		JobKey jobKey = new JobKey(ticket.getTicketId(), ticket.getOwnerId());
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if (jobDetail != null) {
			// Job exist, throw sth
			return;
		} else {
			// Job doesn't exist
			// -> create
			jobDetail = createJobDetail(ticket);

			Trigger trigger = createTrigger(ticket);

			scheduler.scheduleJob(jobDetail, trigger);

			scheduler.getListenerManager().addJobListener(new AlertTicketJobListener(ticketService));
		}
	}

	@Override
	public void stopJob(String ticketId) throws SchedulerException, BadInputException {
		Ticket ticket = ticketService.findByTicketId(ticketId);
		if (ticket == null) {
			// throw new BadInputException("Can not find script with id: " + script_id);
			return;
		}
		if (ticket.getAlert().getMode().equals(Boolean.FALSE.toString())) {
			// throw new BadInputException("Can not stop an already-stopped script!");
			return;
		}

		JobKey jobKey = new JobKey(ticket.getTicketId(), ticket.getOwnerId());
		// scheduler.pauseJob(jobKey);

		// pause job problem: when resume, it execute the
		// job 1 extra time if it "misfire" - even though, setting misfire to do nothing
		// will not work because old nextFireTime < currentTime
		// https://stackoverflow.com/questions/1933676/quartz-java-resuming-a-job-excecutes-it-many-times

		scheduler.deleteJob(jobKey);

	}

	private JobDetail createJobDetail(Ticket ticket) {
		return JobBuilder.newJob(AlertTicketJob.class).withIdentity(ticket.getTicketId(), ticket.getOwnerId()).build();
	}

	private Trigger createTrigger(Ticket ticket) throws IOException {
		JobKey jobKey = new JobKey(ticket.getTicketId(), ticket.getOwnerId());
		return TriggerBuilder.newTrigger().withIdentity(ticket.getTicketId(), ticket.getOwnerId())
				.withSchedule(CronScheduleBuilder.cronSchedule(ticket.getAlert().getCronExpression())).forJob(jobKey)
				.build();
	}

}
