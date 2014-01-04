package com.rowe.trivia.repo.dataStore;

/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;

/**
 * {@link UsersConnectionRepository} that uses the AppEngine Datastore API to persist connection data.
 * @author Vladislav Tserman
 * {@link https://github.com/vtserman/spring-social-appengine/blob/master/}
 */
public class DatastoreUsersConnectionRepository implements UsersConnectionRepository {        
        private final DatastoreService datastore;
        
        private final ConnectionFactoryLocator connectionFactoryLocator;
        private final TextEncryptor textEncryptor;
        private ConnectionSignUp connectionSignUp;        
        private String kindPrefix = "";
        
        public DatastoreUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator, 
        		TextEncryptor textEncryptor, 
        		DatastoreService datastore) {
                this.connectionFactoryLocator = connectionFactoryLocator;
                this.textEncryptor = textEncryptor;
                this.datastore = datastore;
        }
        
        /** Returns kind of the entity */
        private String getKind() {
                return "UserConnection";
        }
        
        @Override
        public List<String> findUserIdsWithConnection(Connection<?> connection) {
                ConnectionKey key = connection.getKey();
                final CompositeFilter filter = CompositeFilterOperator.and(
                        FilterOperator.EQUAL.of("providerId", key.getProviderId()),
                        FilterOperator.EQUAL.of("providerUserId", key.getProviderUserId())
                );
                // fetch and return only keys, not full entities.
                Query query = new Query(getKind()).setFilter(filter).setKeysOnly();
                List<String> localUserIds = entitiesToUserIds(datastore.prepare(query).asIterable());
                if (localUserIds.size() == 0 && connectionSignUp != null) {                        
                    String newUserId = connectionSignUp.execute(connection);
                    if (newUserId != null) {
                            createConnectionRepository(newUserId).addConnection(connection);
                            return Arrays.asList(newUserId);
                    }
                }
                return localUserIds;
        }

        private List<String> entitiesToUserIds(Iterable<Entity> iterable) {
			List<String> userIds = new ArrayList<String>();
			for(Entity entity:iterable){
				Key key = entity.getKey().getParent();
	            userIds.add(key.getName());
			}
			return userIds;
		}

		@Override
        public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
                final CompositeFilter filter = CompositeFilterOperator.and(
                        FilterOperator.EQUAL.of("providerId", providerId),
                        FilterOperator.IN.of("providerUserId", providerUserIds)
                );
                // fetch and return only keys, not full entities.
                Query query = new Query(getKind()).setFilter(filter).setKeysOnly();
                List<String> resultList = entitiesToUserIds(datastore.prepare(query).asIterable());
                return new HashSet<String>(resultList);
        }
        
        @Override
        public ConnectionRepository createConnectionRepository(String userId) {
                if (userId == null) throw new IllegalArgumentException("userId cannot be null");
                DatastoreConnectionRepository repo = new DatastoreConnectionRepository(userId, connectionFactoryLocator, textEncryptor, datastore);
                return repo;
        }


        /**
         * The command to execute to create a new local user profile in the event no user id could be mapped to a connection.
         * Allows for implicitly creating a user profile from connection data during a provider sign-in attempt.
         * Defaults to null, indicating explicit sign-up will be required to complete the provider sign-in attempt.
         * @see #findUserIdsWithConnection(Connection)
         */
        public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
                this.connectionSignUp = connectionSignUp;
        }
}