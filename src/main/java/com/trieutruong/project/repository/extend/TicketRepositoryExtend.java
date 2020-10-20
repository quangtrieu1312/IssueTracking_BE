package com.trieutruong.project.repository.extend;

import java.util.List;

import com.trieutruong.project.domain.Ticket;
import com.trieutruong.project.exception.BadInputException;
import com.trieutruong.project.request.TicketRequest;

public interface TicketRepositoryExtend {
	Ticket update(String ticketId, TicketRequest request) throws BadInputException;
}
