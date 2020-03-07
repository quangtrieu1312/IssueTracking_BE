package com.trieutruong.webpage.service;

import java.io.IOException;

import org.quartz.SchedulerException;

public interface QuartzSchedulerService {

	void startJob(String ticketId) throws SchedulerException, IOException;
	
	void stopJob(String ticketId) throws SchedulerException;

}
