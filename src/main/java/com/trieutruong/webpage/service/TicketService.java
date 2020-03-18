package com.trieutruong.webpage.service;

import java.util.List;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.exception.BadInputException;
import com.trieutruong.webpage.model.TicketInfo;
import com.trieutruong.webpage.request.TicketRequest;

public interface TicketService {

	Ticket create(Ticket ticket);
	
	Ticket create(TicketRequest request, HttpServletRequest httpRequest) throws BadInputException;
	
	List<Ticket> findByUserId(String userId);
	
	Ticket setStatusByTicketId(String ticketId);
	
	Long deleteByTicketId(String ticketId);
	
	List<Ticket> findByHttpRequest(HttpServletRequest httpRequest) throws BadInputException;

	Ticket update(String ticketId, TicketRequest request, HttpServletRequest httpRequest) throws BadInputException;

	List<Ticket> findByAlertMode(Boolean mode);

	Ticket findByTicketId(String ticketId) throws BadInputException;

	List<TicketInfo> convertTicketToTicketInfo(List<Ticket> tickets) throws BadInputException;

	Ticket delete(String ticketId, HttpServletRequest httpRequest) throws BadInputException;

	Page<Ticket> findPageByHttpRequest(Pageable pageable, HttpServletRequest httpRequest) throws BadInputException;

}
