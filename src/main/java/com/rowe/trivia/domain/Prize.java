package com.rowe.trivia.domain;

import com.googlecode.objectify.annotation.Embed;

/**
 * Holds information about a prize that can be won
 * @author paulrowe
 *
 */
@Embed
public class Prize {

	private String title;
	
	public Prize(String title) {
		this.title = title;
	}
	public Prize() {
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
