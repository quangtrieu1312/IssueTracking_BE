package com.trieutruong.webpage.request;

import java.util.List;

import com.trieutruong.webpage.constant.TicketStatus;
import com.trieutruong.webpage.model.Alert;

public class TicketRequest {
	
	private String name;
	private String status = TicketStatus.DEFAULT;
	private String description;
	private Alert alert;
	private List<String> emails;
	private List<String> userIds;

	public TicketRequest() {

	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Alert getAlert() {
		return alert;
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




}
