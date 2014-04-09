package com.rowe.trivia.questionGeneration;

import static org.junit.Assert.*;

import org.junit.Test;

public class CitiesByPopulationQuestionGeneratorTest {

	@Test
	public void test() {
		new CitiesByPopulationQuestionGenerator().generateQuestions();
	}

}
