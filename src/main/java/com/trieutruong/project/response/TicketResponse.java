package com.trieutruong.project.response;

import java.util.List;

import com.trieutruong.project.model.GeneralModelResponse;
import com.trieutruong.project.model.TicketInfo;

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
