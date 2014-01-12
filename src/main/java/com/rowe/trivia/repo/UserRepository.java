package com.rowe.trivia.repo;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetailsService;

import com.rowe.trivia.domain.User;

public interface UserRepository extends UserDetailsService, SocialUserDetailsService, BaseRepository<User>{

	/**
	 * Saves a new user making sure not to overwrite an existing user
	 * @param user
	 */
	public void saveNewUser(User user) throws UsernameTakenExeption;
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException;

	/**
	 * Is the provided username available in the system
	 * @param value
	 * @return
	 */
	public boolean isUsernameAvailable(String username);

	public static class UsernameTakenExeption extends RuntimeException {
		private static final long serialVersionUID = 1L;
		private String takenUsername;
		
		public UsernameTakenExeption(String takenUsername) {
			super("The username " + takenUsername + " is already in use");
			this.takenUsername = takenUsername;
		}
		
		public String getTakenUsername() {
			return takenUsername;
		}
	}
}
