package com.trieutruong.webpage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.exception.BadInputException;
import com.trieutruong.webpage.model.TicketInfo;
import com.trieutruong.webpage.request.TicketRequest;

public interface TicketService {
	
	Ticket create(Ticket ticket);
	
	Ticket create(TicketRequest request, HttpServletRequest httpRequest) throws BadInputException;
	
	List<Ticket> findByUserId(String userId);
	
	Ticket setStatusByTicketId(String ticketId);
	
	Ticket deleteByTicketId(String ticketId);
	
	List<Ticket> findByHttpRequest(HttpServletRequest httpRequest) throws BadInputException;

	Ticket update(String ticketId, TicketRequest request, HttpServletRequest httpRequest) throws BadInputException;

	List<Ticket> findByAlertMode(String string);

	Ticket findByTicketId(String ticketId);

	List<TicketInfo> convertTicketToTicketInfo(List<Ticket> tickets) throws BadInputException;
}
