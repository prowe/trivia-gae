package com.rowe.trivia.repo.objectify;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.rowe.trivia.repo.BaseRepository;

abstract class ObjectifyRepositorySupport<T> implements BaseRepository<T> {

	@Override
	public void save(T obj) {
		ObjectifyService.ofy().save().entity(obj);
	}

	@Override
	public List<T> listAll() {
		return ObjectifyService.ofy().load().type(getEntityType()).list();
	}

	protected abstract Class<T> getEntityType();
}
