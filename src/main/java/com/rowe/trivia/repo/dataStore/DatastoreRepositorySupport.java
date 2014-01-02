package com.rowe.trivia.repo.dataStore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.rowe.trivia.repo.BaseRepository;

public abstract class DatastoreRepositorySupport<T> implements BaseRepository<T>{

	@Autowired
	protected DatastoreService datastoreService;
	
	@Override
	public void save(T contest) {
		Entity entity = toEntity(contest);
		datastoreService.put(entity);
	}
	
	@Override
	public List<T> listAll() {
		Query query = new Query(getKind());
		Iterator<Entity> it = datastoreService.prepare(query).asIterator();
		List<T> results = new ArrayList<T>();
		while(it.hasNext()){
			results.add(fromEntity(it.next()));
		}
		return results;
	}
	
	protected String getKind(){
		return getEntityType().getSimpleName();
	}
	
	protected abstract Class<T> getEntityType();
	
	protected abstract Entity toEntity(T obj);
	
	protected abstract T fromEntity(Entity entity);
	
	protected class ConvertingIterable implements Iterable<T> {
		private final Iterable<Entity> entityIterable;
		
		protected ConvertingIterable(Iterable<Entity> entityIterable) {
			this.entityIterable = entityIterable;
		}
		@Override
		public Iterator<T> iterator() {
			return new ConvertingIterator(entityIterable.iterator());
		}
	}
	
	protected class ConvertingIterator implements Iterator<T> {

		private final Iterator<Entity> iterator;

		public ConvertingIterator(Iterator<Entity> iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public T next() {
			return fromEntity(iterator.next());
		}

		@Override
		public void remove() {
			iterator.remove();
		}
		
	}
}
