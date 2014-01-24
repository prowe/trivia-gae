package com.rowe.trivia.domain;

import java.math.BigDecimal;

import com.googlecode.objectify.annotation.Embed;

/**
 * Holds information about a prize that can be won
 * @author paulrowe
 *
 */
@Embed
public class Prize {
	public enum RedemptionMethod {
		MAIL,
		EMAIL
	};

	private String title;
	private String description;
	private BigDecimal value;
	private RedemptionMethod redemptionMethod;
	
	public boolean isAddressRequired(){
		return redemptionMethod == RedemptionMethod.MAIL;
	}
	
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public BigDecimal getValue() {
		return value;
	}
	public RedemptionMethod getRedemptionMethod() {
		return redemptionMethod;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public void setRedemptionMethod(RedemptionMethod redemptionMethod) {
		this.redemptionMethod = redemptionMethod;
	}
}
