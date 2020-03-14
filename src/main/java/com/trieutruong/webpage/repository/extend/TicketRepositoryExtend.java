package com.trieutruong.webpage.repository.extend;

import java.util.List;

import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.exception.BadInputException;
import com.trieutruong.webpage.request.TicketRequest;

public interface TicketRepositoryExtend {
	Ticket update(String ticketId, TicketRequest request) throws BadInputException;
}
