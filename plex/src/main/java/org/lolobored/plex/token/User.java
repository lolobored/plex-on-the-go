package org.lolobored.plex.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The access token class contains the token which will be used to communicate with Plex
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private String authentication_token;

	/**
	 * Gets the token as a String
	 * @return the authentication token
	 */
	public String getAuthentication_token() {
		return authentication_token;
	}

	/**
	 * Sets the token
	 * @param authentication_token the authentication_token to set
	 */
	public void setAuthentication_token(String authentication_token) {
		this.authentication_token = authentication_token;
	}
}
