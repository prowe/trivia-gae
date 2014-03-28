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

import com.rowe.trivia.domain.Question;
import com.rowe.trivia.repo.QuestionRepository;

@Controller
@RequestMapping("/questions/create.html")
public class CreateQuestionController {
	private static Logger logger = LoggerFactory.getLogger(CreateQuestionController.class);

	@Autowired
	private QuestionRepository questionRepo;
	
	@ModelAttribute("question")
	public Question createNewQuestion(){
		return new Question();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String get(){
		return "questions/addEdit";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String save(@Valid @ModelAttribute Question question, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "questions/addEdit";
		}
		question.completeCreation();
		questionRepo.save(question);
		logger.info("Saved question {}", question);
		return "redirect:/questions/list.html";
	}
}
