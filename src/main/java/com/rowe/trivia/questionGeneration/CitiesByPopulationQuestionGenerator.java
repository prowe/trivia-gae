package com.rowe.trivia.questionGeneration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.rowe.trivia.domain.Question;

public class CitiesByPopulationQuestionGenerator {
	private static Logger logger = LoggerFactory.getLogger(CitiesByPopulationQuestionGenerator.class);
	private Resource file = new ClassPathResource("top5000CitiesByPop.tsv", CitiesByPopulationQuestionGenerator.class);

	public List<Question> generateQuestions(){
		logger.info("Generating questions from {}", file);
		List<FileRow> rows = readRows();
		Collections.sort(rows, Collections.reverseOrder());
		List<Question> questions = new ArrayList<>();
		for(int i=0; i < Math.min(rows.size() - 3, 1000); i++){
			String correct = rows.get(i).buildAnswer();
			Question question = new Question("Which of these cities has the largest population?",
				correct,
				rows.get(i+1).buildAnswer(),
				rows.get(i+2).buildAnswer(),
				rows.get(i+3).buildAnswer());
			questions.add(question);
		}
		logger.info("Generated {} questions", questions.size());
		return questions;
	}
	
	private List<FileRow> readRows(){
		try {
			List<String> lines = IOUtils.readLines(file.getInputStream());
			List<FileRow> rows = new ArrayList<FileRow>();
			for(String line:lines){
				String[] split = line.split("\t");
				rows.add(new FileRow(split[0], split[1], Integer.valueOf(split[2])));
			}
			return rows;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static class FileRow implements Comparable<FileRow> {
		private String city;
		private String state;
		private int population;
		
		public FileRow(String city, String state, int population) {
			super();
			this.city = city;
			this.state = state;
			this.population = population;
		}
		
		@Override
		public int compareTo(FileRow o) {
			return Integer.compare(population, o.population);
		}
		
		public String buildAnswer(){
			return StringUtils.trim(city)
				+ ", " + StringUtils.trim(state).toUpperCase();
		}
	}
}
