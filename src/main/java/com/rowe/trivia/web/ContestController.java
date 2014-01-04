package com.rowe.trivia.web;

import java.net.URI;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriTemplate;

import com.rowe.trivia.domain.Contest;
import com.rowe.trivia.repo.ContestRepository;

@Controller
@RequestMapping("/contests")
public class ContestController {
	private static Logger logger = LoggerFactory.getLogger(ContestController.class);

	@Autowired
	private ContestRepository contestRepo;
	
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
	
	@RequestMapping("create.html")
	public void createNewContest(){
		logger.info("createNewContest");
	}
	
	@RequestMapping(value="create.html", method=RequestMethod.POST)
	public String submitNewContest(@ModelAttribute("contest") Contest contest){
		contest.completeCreation();
		contest.save();
		logger.info("Submitted Contest: {}", contest);
		
		URI expanded = redirectToView(contest);
		return expanded.toString();
	}

	private URI redirectToView(Contest contest) {
		URI expanded = new UriTemplate("redirect:/contests/{userName}/{contestId}/view.html")
			.expand(contest.getSponsor().getUsername(), contest.getContestId());
		return expanded;
	}
	
	@RequestMapping(value="{userName}/{contestId}/view.html")
	public String viewContest(
		@PathVariable("userName") String userName,
		@PathVariable("contestId") String contestId,
		Map<String, Object> modelMap){
		Contest contest = contestRepo.getContest(userName, contestId);
		modelMap.put("contest", contest);
		
		return "contests/view";
	}
	
	@RequestMapping(value="{userName}/{contestId}/start.html")
	public String startContest(
		@PathVariable("userName") String userName,
		@PathVariable("contestId") String contestId,
		Map<String, Object> modelMap) {
		Contest contest = contestRepo.getContest(userName, contestId);
		logger.info("Starting contest");
		contest.start();
		contestRepo.save(contest);
		return redirectToView(contest).toString();
	}
	
	@RequestMapping(value="{userName}/{contestId}/stop.html")
	public String endContest(
		@PathVariable("userName") String userName,
		@PathVariable("contestId") String contestId,
		Map<String, Object> modelMap) {
		Contest contest = contestRepo.getContest(userName, contestId);
		logger.info("Ending contest");
		contest.end();
		contestRepo.save(contest);
		return redirectToView(contest).toString();
	}
}
