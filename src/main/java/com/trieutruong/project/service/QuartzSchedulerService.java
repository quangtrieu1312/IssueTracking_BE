package com.trieutruong.project.service;

import java.io.IOException;

import org.quartz.SchedulerException;

import com.trieutruong.project.exception.BadInputException;

public interface QuartzSchedulerService {

	void startJob(String ticketId) throws SchedulerException, IOException, BadInputException;
	
	void stopJob(String ticketId) throws SchedulerException, BadInputException;

}
