package com.rowe.trivia.repo;

import com.rowe.trivia.domain.Question;

public interface QuestionRepository extends BaseRepository<Question>{

	public Question getById(String id);
}
