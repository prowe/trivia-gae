package com.rowe.trivia.repo;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.impl.ref.LiveRef;

public class BetterRef<T> extends LiveRef<T> {

	public static <T> Ref<T> create(T object){
		return new BetterRef<T>(object);
	}
	
	private transient T entity;
	
	private BetterRef(T object) {
		super(Key.create(object));
		this.entity = object;
	}
	
	@Override
	public T get() {
		T superObj = super.get();
		if(superObj != null){
			return superObj;
		}else{
			return entity;
		}
	}
}
