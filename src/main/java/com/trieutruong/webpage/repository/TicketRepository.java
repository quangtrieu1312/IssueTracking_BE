package com.trieutruong.webpage.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.trieutruong.webpage.domain.Ticket;
import com.trieutruong.webpage.repository.extend.TicketRepositoryExtend;
import com.trieutruong.webpage.request.TicketRequest;

public interface TicketRepository extends MongoRepository<Ticket, String>, TicketRepositoryExtend{
	
	@Query("{ 'ticketId' : ?0 }")
	Ticket findByTicketId(String ticketId);
	
	@Query("{ $or: [{ memberIds : ?0 }, { ownerId: ?0 }]}")
	List<Ticket> findByMemberIdsOrOwnerId(String userId);

	@Query("{ 'alert.mode' : ?0 }")
	List<Ticket> findByAlertMode(Boolean mode);

	@DeleteQuery
	Long deleteByTicketId(String ticketId);

	@Query("{ $or: [{ memberIds : ?0 }, { ownerId: ?0 }]}")
	Page<Ticket> findPageByMemberIdsOrOwnerId(String userId, Pageable pageable);

	@Query("{$and: [{ $or : [{ memberIds : ?0 }, { ownerId: ?0 }] }, { $or : [{ name : { $regex : ?1 } }, { description : { $regex : ?1 }}] }] }")
	Page<Ticket> findPageByMemberIdsOrOwnerIdAndSearchBox(String userId, String searchBox, Pageable pageable);

}
