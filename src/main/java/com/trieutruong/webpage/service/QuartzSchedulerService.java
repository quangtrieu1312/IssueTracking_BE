package com.trieutruong.webpage.service;

import java.io.IOException;

import org.quartz.SchedulerException;

import com.trieutruong.webpage.exception.BadInputException;

public interface QuartzSchedulerService {

	void startJob(String ticketId) throws SchedulerException, IOException, BadInputException;
	
	void stopJob(String ticketId) throws SchedulerException, BadInputException;

}
