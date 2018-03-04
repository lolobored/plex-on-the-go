package org.lolobored.plex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * The season object contains every information for a particular season of a tvshow
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Season {
	private Integer seasonNumber;
	private String summary;


}
