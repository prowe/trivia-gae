package com.rowe.trivia.repo;

import java.util.List;


public interface BaseRepository<T> {

	public void save(T obj);
	
	public List<T> listAll();
}
