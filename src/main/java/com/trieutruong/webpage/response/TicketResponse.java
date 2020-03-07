package com.trieutruong.webpage.response;

import com.trieutruong.webpage.model.GeneralModelResponse;

import java.util.List;

import com.trieutruong.webpage.domain.Ticket;

public class TicketResponse extends GeneralModelResponse{
	private List<Ticket> tickets;
	
	public TicketResponse(String msg, List<Ticket> tickets)
	{
		super.setStatus(Boolean.TRUE.toString());
		super.setMsg(msg);
		this.tickets = tickets;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	
}
