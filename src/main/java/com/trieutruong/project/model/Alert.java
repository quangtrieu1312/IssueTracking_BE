package com.trieutruong.project.model;

import com.trieutruong.project.annotation.ValidCron;
import com.trieutruong.project.annotation.ValidTimezone;

public class Alert {
	private Boolean mode = false;
	@ValidTimezone
	private String timezone;
	@ValidCron
	private String cronExpression;
	public Alert() {
		
	}
	
	public Alert(Boolean mode, String timezone, String cronExpression) {
		this.mode = mode;
		this.timezone = timezone;
		this.cronExpression = cronExpression;
	}

	public Boolean getMode() {
		return mode;
	}

	public void setMode(Boolean mode) {
		this.mode = mode;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
}
