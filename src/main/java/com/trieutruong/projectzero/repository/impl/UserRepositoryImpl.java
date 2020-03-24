package com.trieutruong.projectzero.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.trieutruong.projectzero.constant.DomainName;
import com.trieutruong.projectzero.domain.User;
import com.trieutruong.projectzero.repository.extend.UserRepositoryExtend;

@Repository
public class UserRepositoryImpl implements UserRepositoryExtend {

	private static final String doc = DomainName.USER;
	private static final Class docClass = User.class;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void enable(String userId) {
		Query query = new Query(Criteria.where("userId").is(userId));
		Update update = new Update();
		update.set("enabled", true);
		mongoTemplate.updateFirst(query, update, docClass);
	}

}
