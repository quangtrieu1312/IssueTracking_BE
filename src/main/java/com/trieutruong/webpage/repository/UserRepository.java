package com.trieutruong.webpage.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.trieutruong.webpage.domain.User;
import com.trieutruong.webpage.repository.extend.UserRepositoryExtend;

public interface UserRepository extends MongoRepository<User,String>, UserRepositoryExtend{
	
	User findByUsername(String username);

	@Query("{ 'user_id' : ?0 }")
	User findByUser_id(String userId);
	
}
