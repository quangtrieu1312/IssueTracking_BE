package com.trieutruong.webpage.response;

import com.trieutruong.webpage.model.GeneralModelResponse;
import com.trieutruong.webpage.model.TicketInfo;

import java.util.List;

public class TicketResponse extends GeneralModelResponse{
	private List<TicketInfo> ticketsInfo;
	
	public TicketResponse(String msg, List<TicketInfo> ticketsInfo)
	{
		super.setStatus(Boolean.TRUE.toString());
		super.setMsg(msg);
		this.ticketsInfo = ticketsInfo;
	}

	public List<TicketInfo> getTicketsInfo() {
		return ticketsInfo;
	}

	public void setTicketsInfo(List<TicketInfo> ticketsInfo) {
		this.ticketsInfo = ticketsInfo;
	}
	
}
