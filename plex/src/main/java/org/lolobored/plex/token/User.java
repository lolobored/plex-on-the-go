package org.lolobored.plex.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * The access token class contains the token which will be used to communicate with Plex
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private String authentication_token;


}
