package com.rowe.trivia.web;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rowe.trivia.domain.Question;
import com.rowe.trivia.repo.QuestionRepository;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	private static Logger logger = LoggerFactory.getLogger(QuestionController.class);

	@Autowired
	private QuestionRepository questionRepo;
	
	
	@RequestMapping(value="list.html")
	public void listQuestions(Map<String, Object> modelMap){
		modelMap.put("questionList", questionRepo.listAll());
	}
	
}
