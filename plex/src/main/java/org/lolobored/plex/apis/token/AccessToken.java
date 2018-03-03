package org.lolobored.plex.apis.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The AccessToken class is the class returned by Plex when authenticating
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessToken {
	private User user;

	/**
	 * Returns the user
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
