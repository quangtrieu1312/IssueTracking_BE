package com.trieutruong.projectzero.request;

import java.util.List;

import javax.validation.Valid;

import com.trieutruong.projectzero.constant.TicketStatus;
import com.trieutruong.projectzero.model.Alert;

public class TicketRequest {

	private String name;
	private String status = TicketStatus.DEFAULT;
	private String description;
	@Valid
	private Alert alert;
	private List<String> emails;
	private List<String> members;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

}
