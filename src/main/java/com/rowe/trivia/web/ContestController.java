package com.rowe.trivia.web;

import java.net.URI;
import java.util.List;
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
import org.springframework.web.util.UriTemplate;

import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.domain.Question;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.repo.QuestionRepository;

@Controller
@RequestMapping("/contests")
public class ContestController {
	private static Logger logger = LoggerFactory.getLogger(ContestController.class);

	@Autowired
	private ContestRepository contestRepo;
	@Autowired
	private QuestionRepository questionRepo;
	
	@RequestMapping("list.html")
	public void list(Map<String, Object> modelMap){
		modelMap.put("contestList", contestRepo.listAll());
	}
	
	@ModelAttribute("contest")
	public Contest create(){
		Contest contest = new Contest();
		logger.info("Created contest {}", contest);
		return contest;
	}
	@ModelAttribute("questionList")
	public List<Question> getQuestionList(){
		return questionRepo.listAll();
	}
	
	@RequestMapping("create.html")
	public void createNewContest(){
		logger.info("createNewContest");
	}
	
	@RequestMapping(value="create.html", method=RequestMethod.POST)
	public String submitNewContest(@Valid @ModelAttribute("contest") Contest contest, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return null;
		}
		logger.info("Submitted Contest: {}", contest);
		
		URI expanded = redirectToView(contest);
		return expanded.toString();
	}

	private URI redirectToView(Contest contest) {
		URI expanded = new UriTemplate("redirect:/contests/{contestId}/view.html").expand(contest.getContestId());
		return expanded;
	}
	
	@RequestMapping(value="{contestId}/prizeDetails.html")
	public String prizeDetails(@PathVariable("contestId") String contestId,
			Map<String, Object> modelMap) {
		Contest contest = contestRepo.getContest(contestId);
		modelMap.put("contest", contest);
		modelMap.put("prize", contest.getPrize());
		return "contests/prizeDetails";
	}
	
	@RequestMapping(value="{contestId}/view.html")
	public String viewContest(
		@PathVariable("contestId") String contestId,
		Map<String, Object> modelMap){
		Contest contest = contestRepo.getContest(contestId);
		modelMap.put("contest", contest);
		
		return "contests/view";
	}
	
	@RequestMapping(value="{contestId}/end.html", method=RequestMethod.POST)
	public String endContest(
		@PathVariable("contestId") String contestId,
		Map<String, Object> modelMap) {
		Contest contest = contestRepo.getContest(contestId);
		logger.info("Ending contest");
		contest.end();
		contestRepo.save(contest);
		return "contests/end";
	}
}
