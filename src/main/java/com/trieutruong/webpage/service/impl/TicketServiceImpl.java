package com.trieutruong.webpage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.domain.User;
import com.trieutruong.webpage.exception.BadInputException;
import com.trieutruong.webpage.model.TicketInfo;
import com.trieutruong.webpage.repository.TicketRepository;
import com.trieutruong.webpage.request.TicketRequest;
import com.trieutruong.webpage.service.TicketService;
import com.trieutruong.webpage.service.UserService;
import com.trieutruong.webpage.util.RandomUtil;

@Service
public class TicketServiceImpl implements TicketService {

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
		User owner = userService.findByHttpRequest(httpRequest);
		while (ticketRepository.findByTicketId(ticketId) != null) {
			ticketId = RandomUtil.generateId();
		}
		List<String> userIds = new ArrayList<String>();
		try {

			List<User> users = userService.findByUsernames(request.getMembers());

			for (User userIterator : users) {
				userIds.add(userIterator.getUserId());
			}

		} catch (Exception e) {

		}
		Ticket ticket = new Ticket(ticketId, owner.getUserId(), request.getName(), request.getStatus(),
				request.getDescription(), request.getAlert(), request.getEmails(), userIds);
		ticketRepository.save(ticket);
		return ticket;
	}

	@Override
	public Ticket update(String ticketId, TicketRequest request, HttpServletRequest httpRequest)
			throws BadInputException {
		User user = userService.findByHttpRequest(httpRequest);
		if (user == null) {
			// throw exception
			return null;
		}
		Ticket ticket = ticketRepository.findByTicketId(ticketId);
		if (ticket == null) {
			// throw exception
			return null;
		}
		if (ticket.getOwnerId().equals(user.getUserId())) {
			return ticketRepository.update(ticketId, request);
		} else {
			// throw exception
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

	@Override
	public List<TicketInfo> convertTicketToTicketInfo(List<Ticket> tickets) throws BadInputException {
		List<TicketInfo> ticketsInfo = new ArrayList<TicketInfo>();
		for (Ticket ticket : tickets) {
			// get owner's username
			String owner = userService.findByUserId(ticket.getOwnerId()).getUsername();
			// get members' usernames - which members can be null cause MongoDB error
			List<String> members = new ArrayList<String>();
			try {
				List<User> users = userService.findByUserIds(ticket.getMemberIds());
				for (User user : users) {
					members.add(user.getUsername());
				}
			} catch (Exception e) {

			}
			// create new single-ticket info
			TicketInfo ticketInfo = new TicketInfo();
			ticketInfo.setTicketId(ticket.getTicketId());
			ticketInfo.setName(ticket.getName());
			ticketInfo.setOwner(owner);
			ticketInfo.setStatus(ticket.getStatus());
			ticketInfo.setAlert(ticket.getAlert());
			ticketInfo.setMembers(members);
			ticketInfo.setEmails(ticket.getEmails());
			ticketInfo.setDescription(ticket.getDescription());
			// add single-ticket info to list
			ticketsInfo.add(ticketInfo);
		}
		return ticketsInfo;
	}

}
