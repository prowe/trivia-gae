package com.rowe.trivia.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import com.googlecode.objectify.annotation.Embed;

@Embed
public class Address implements Serializable{
	private static final long serialVersionUID = -6373552100795466770L;
	
	@NotBlank
	private String line1;
	private String line2;
	
	@NotBlank
	private String city;
	@NotNull
	private StateCode state;
	
	@NotBlank
	@Size(min=5, max=5)
	private String zip;
	
	public boolean isEmpty(){
		return StringUtils.isBlank(line1)
			&& StringUtils.isBlank(line2)
			&& StringUtils.isBlank(city)
			&& state == null
			&& StringUtils.isBlank(zip);
	}
	
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public StateCode getState() {
		return state;
	}
	public void setState(StateCode state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
}
