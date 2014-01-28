package com.rowe.trivia.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.twitter.api.Twitter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.rowe.trivia.domain.validation.PasswordConfirmed;
import com.rowe.trivia.repo.ContestRepository;
import com.rowe.trivia.repo.UserRepository;
import com.rowe.trivia.service.EmailService;

@Entity
@Configurable
@PasswordConfirmed
public class User implements UserDetails, SocialUserDetails{
	private static final long serialVersionUID = 1L;
	
	@Autowired @Ignore
	private transient UserRepository repo;
	@Autowired @Ignore
	private transient ContestRepository contestRepo;
	@Autowired @Ignore
	private transient EmailService emailService;
	@Autowired @Ignore
	private transient UsersConnectionRepository usersConnectionRepository;
	
	//using email as username
	//can't rename this field because it is the ID
	@Id
	@NotBlank 
	@Email
	private String username;
	
	@NotBlank
	private String displayName;
	
	@NotBlank
	private String password;
	@NotBlank
	private String passwordConfirmation;
	
	/**
	 * Has this user elected to receive email communication?
	 */
	private boolean emailNotificationEnabled = false;
	
	//TODO: add birthdate
	private String phoneNumber;
	
	@Valid
	private Address address;
	
	
	public User() {
	}
	public User(String username) {
		setUsername(username);
	}
	
	public static User currentUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null){
			return null;
		}
		Object principal = authentication.getPrincipal();
		if(principal instanceof User){
			return (User) principal;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "User [" + getUsername() + "]";
	}
	
	@Override
	public String getUserId() {
		return getUsername();
	}
	@Override
	public String getUsername() {
		return getEmail();
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getUsername() == null) ? 0 : getUsername().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if(obj instanceof User){
			User right = (User)obj;
			return StringUtils.equals(getUsername(), right.getUsername());
		}
		return false;
	}

	/* UserDetails implementation */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthList = new ArrayList<GrantedAuthority>();
		grantedAuthList.add(new SimpleGrantedAuthority("ROLE_USER"));
		if("paul.w.rowe@gmail.com".equalsIgnoreCase(getEmail())){
			grantedAuthList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return grantedAuthList;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void save() {
		repo.save(this);
	}

	/**
	 * Sign in this user as the current user. Making this user the current user returned by {@link User#currentUser()}
	 */
	public void signInAsCurrentUser() {
		Authentication authenticationToken = new PreAuthenticatedAuthenticationToken(this, null, getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	public String getEmail() {
		return username;
	}
	public void setEmail(String email) {
		this.username = email;
	}
	
	public void setUsername(String username){
		setEmail(username);
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Sign up for all inProgress contests
	 */
	public void signUpForInProgressContests() {
		List<Contest> inProgressContests = contestRepo.findInProgressContests();
		for(Contest contest:inProgressContests){
			UserQuestion uq = new UserQuestion(this, contest);
			uq.save();
		}
	}

	public boolean isEmailNotificationEnabled() {
		return emailNotificationEnabled;
	}
	public void setEmailNotificationEnabled(boolean emailNotificationEnabled) {
		this.emailNotificationEnabled = emailNotificationEnabled;
	}
	
	public boolean isTwitterConnected(){
		return getTwitterConnection() != null;
	}
	
	public Connection<Twitter> getTwitterConnection(){
		ConnectionRepository connectionRepo = usersConnectionRepository.createConnectionRepository(getUserId());
		return connectionRepo.findPrimaryConnection(Twitter.class);
	}
}
