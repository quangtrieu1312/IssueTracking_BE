package com.trieutruong.webpage.model;

import java.util.Date;

public class Alert {
	private Boolean mode;
	private Date alertTime;
	private String cronExpression;
	public Alert() {
		
	}
	
	public Alert(Boolean mode, Date alertTime, String cronExpression) {
		this.mode = mode;
		this.alertTime = alertTime;
		this.cronExpression = cronExpression;
	}

	public Boolean getMode() {
		return mode;
	}

	public void setMode(Boolean mode) {
		this.mode = mode;
	}

	public Date getAlertTime() {
		return alertTime;
	}

	public void setAlertTime(Date alertTime) {
		this.alertTime = alertTime;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cron) {
		this.cronExpression = cronExpression;
	}
}
