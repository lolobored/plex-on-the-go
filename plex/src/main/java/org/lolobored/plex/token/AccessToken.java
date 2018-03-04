package org.lolobored.plex.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * The AccessToken class is the class returned by Plex when authenticating
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessToken {
	private User user;


}
