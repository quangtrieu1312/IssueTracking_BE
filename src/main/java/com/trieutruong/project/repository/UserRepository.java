package com.trieutruong.project.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.trieutruong.project.domain.User;
import com.trieutruong.project.repository.extend.UserRepositoryExtend;

public interface UserRepository extends MongoRepository<User,String>, UserRepositoryExtend{
	
	User findByUsername(String username);

	User findByUserId(String userId);

	@Query("{ 'userId' : { $in : ?0 } }")
	List<User> findByUserIds(List<String> userIds);

	@Query("{ 'username' : { $in: ?0 } }")
	List<User> findByUsernames(List<String> usernames);
	
}
