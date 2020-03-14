package com.trieutruong.webpage.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.trieutruong.webpage.domain.User;
import com.trieutruong.webpage.repository.extend.UserRepositoryExtend;

public interface UserRepository extends MongoRepository<User,String>, UserRepositoryExtend{
	
	User findByUsername(String username);

	User findByUserId(String userId);

	@Query("{ 'userId' : { $in : ?0 } }")
	List<User> findByUserIds(List<String> userIds);

	@Query("{ 'username' : {$in: ?0 } }")
	List<User> findByUsernames(List<String> usernames);
	
}
