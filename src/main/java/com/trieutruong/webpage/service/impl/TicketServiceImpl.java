package com.trieutruong.webpage.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.domain.User;
import com.trieutruong.webpage.exception.BadInputException;
import com.trieutruong.webpage.repository.TicketRepository;
import com.trieutruong.webpage.request.TicketRequest;
import com.trieutruong.webpage.service.TicketService;
import com.trieutruong.webpage.service.UserService;
import com.trieutruong.webpage.util.RandomUtil;

@Service
public class TicketServiceImpl implements TicketService{

	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	UserService userService;
	
	@Override
	public Ticket create(Ticket ticket) {
		ticketRepository.save(ticket);
		return ticket;
	}

	@Override
	public List<Ticket> findByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket setStatusByTicketId(String ticketId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket deleteByTicketId(String ticketId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ticket> findByHttpRequest(HttpServletRequest httpRequest) throws BadInputException {
		User user = userService.findByHttpRequest(httpRequest);
		return ticketRepository.findByUserId(user.getUserId());
	}

	@Override
	public Ticket create(TicketRequest request, HttpServletRequest httpRequest) throws BadInputException {
		String ticketId = RandomUtil.generateId();
		User user = userService.findByHttpRequest(httpRequest);
		while (ticketRepository.findByTicketId(ticketId)!=null)
		{
			ticketId = RandomUtil.generateId();
		}
		Ticket ticket = new Ticket(ticketId, user.getUserId(), request.getName(), request.getStatus(),request.getDescription(),request.getAlert(), request.getEmails(), request.getUserIds());
		ticketRepository.save(ticket);
		return ticket;
	}

	@Override
	public Ticket update(String ticketId, TicketRequest request, HttpServletRequest httpRequest) throws BadInputException {
		User user = userService.findByHttpRequest(httpRequest);
		if (user == null)
		{
			//throw exception
			return null;
		}
		Ticket ticket = ticketRepository.findByTicketId(ticketId);
		if (ticket == null)
		{
			//throw exception
			return null;
		}
		if (ticket.getOwnerId().equals(user.getUserId()))
		{
			return ticketRepository.update(ticketId, request);
		}
		else
		{
			//throw exception
		}
		return null;
	}

	@Override
	public List<Ticket> findByAlertMode(String mode) {
		return ticketRepository.findByAlertMode(mode);
	}

	@Override
	public Ticket findByTicketId(String ticketId) {
		return ticketRepository.findByTicketId(ticketId);
	}

}
