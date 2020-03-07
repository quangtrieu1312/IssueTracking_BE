package com.trieutruong.webpage.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.trieutruong.webpage.constant.DomainName;
import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.domain.User;
import com.trieutruong.webpage.repository.extend.TicketRepositoryExtend;
import com.trieutruong.webpage.request.TicketRequest;

@Repository
public class TicketRepositoryImpl implements TicketRepositoryExtend {

	private static final String doc = DomainName.TICKET;
	private static final Class docClass = Ticket.class;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Ticket update(String ticketId, TicketRequest request) {
		Query query = new Query(Criteria.where("ticketId").is(ticketId));
		Update update = new Update();
		update.set("name", request.getName());
		update.set("status", request.getStatus());
		update.set("emails", request.getEmails());
		update.set("userIds", request.getUserIds());
		update.set("description", request.getDescription());
		update.set("alert", request.getAlert());
		mongoTemplate.updateFirst(query, update, docClass);
		return (Ticket) mongoTemplate.find(query, docClass);
	}

}
