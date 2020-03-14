package com.trieutruong.webpage.domain;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.trieutruong.webpage.constant.DomainName;
import com.trieutruong.webpage.model.Alert;

@Document(collection = DomainName.TICKET)
public class Ticket {
	private String ticketId;
	private String ownerId;
	private String name;
	private String status;
	private String description;
	private Alert alert;
	private List<String> emails;
	private List<String> memberIds;

	public Ticket() {

	}

	public Ticket(String ticket_id, String ownerId, String name, String status, String description, Alert alert,
			List<String> emails, List<String> userIds) {
		this.ticketId = ticket_id;
		this.ownerId = ownerId;
		this.name = name;
		this.status = status;
		this.description = description;
		this.alert = alert;
		this.emails = emails;
		this.memberIds = userIds;
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

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public List<String> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(List<String> memberIds) {
		this.memberIds = memberIds;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
