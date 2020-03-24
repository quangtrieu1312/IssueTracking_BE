package com.trieutruong.projectzero.repository.extend;

import java.util.List;

import com.trieutruong.projectzero.domain.Ticket;
import com.trieutruong.projectzero.exception.BadInputException;
import com.trieutruong.projectzero.request.TicketRequest;

public interface TicketRepositoryExtend {
	Ticket update(String ticketId, TicketRequest request) throws BadInputException;
}
