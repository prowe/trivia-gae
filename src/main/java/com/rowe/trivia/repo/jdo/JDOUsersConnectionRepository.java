package com.rowe.trivia.repo.jdo;

import java.util.List;
import java.util.Set;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;

public class JDOUsersConnectionRepository implements UsersConnectionRepository{

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String providerId,
			Set<String> providerUserIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
