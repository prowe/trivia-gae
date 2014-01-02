package com.rowe.trivia.domain;

import java.util.Arrays;
import java.util.Collection;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.social.security.SocialUserDetails;

import com.rowe.trivia.repo.UserRepository;

@PersistenceCapable
@Configurable
public class User implements UserDetails, SocialUserDetails{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private transient UserRepository repo;
	
	@PrimaryKey
	private String username;
	
	private String email;
	private String displayName;
	
	private String phoneNumber;
	
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zip;
	
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
		return "User [" + username + "]";
	}
	
	public void setUsername(String userName) {
		this.username = userName;
	}
	
	@Override
	public String getUserId() {
		return getUsername();
	}
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

	/* UserDetails implementation */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return "password";
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
		Authentication authenticationToken = new PreAuthenticatedAuthenticationToken(this, null, Arrays.asList(new SimpleGrantedAuthority("USER")));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	public String getEmail() {
		return email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
