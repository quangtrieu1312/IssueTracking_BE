package com.trieutruong.project.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.trieutruong.project.constant.DomainName;
import com.trieutruong.project.domain.Ticket;
import com.trieutruong.project.domain.User;
import com.trieutruong.project.exception.BadInputException;
import com.trieutruong.project.repository.extend.TicketRepositoryExtend;
import com.trieutruong.project.request.TicketRequest;
import com.trieutruong.project.service.UserService;

@Repository
public class TicketRepositoryImpl implements TicketRepositoryExtend {

	private static final String doc = DomainName.TICKET;
	private static final Class docClass = Ticket.class;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	UserService userService;

	@Override
	public Ticket update(String ticketId, TicketRequest request) throws BadInputException {
		List<String> memberIds = new ArrayList<String>();
		try {
			List<User> users = userService.findByUsernames(request.getMembers());
			for (User user : users) {
				memberIds.add(user.getUserId());
			}
		} catch (Exception e) {

		}
		Query query = new Query(Criteria.where("ticketId").is(ticketId));
		Update update = new Update();
		update.set("name", request.getName());
		update.set("status", request.getStatus());
		update.set("emails", request.getEmails());
		update.set("memberIds", memberIds);
		update.set("description", request.getDescription());
		update.set("alert", request.getAlert());
		mongoTemplate.updateFirst(query, update, docClass);
		return (Ticket) mongoTemplate.findOne(query, docClass);
	}

}
