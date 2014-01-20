package com.rowe.trivia.convert;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.rowe.trivia.domain.Question;
import com.rowe.trivia.repo.QuestionRepository;

public class QuestionConverter implements Converter<String, Question>{

	private QuestionRepository questionRepo;
	
	public QuestionConverter(QuestionRepository questionRepo) {
		this.questionRepo = questionRepo;
	}
	
	@Override
	public Question convert(String source) {
		if(questionRepo == null){
			throw new IllegalStateException("Question repo is null");
		}
		if(StringUtils.isBlank(source)){
			return null;
		}
		return questionRepo.getById(source);
	}
}
