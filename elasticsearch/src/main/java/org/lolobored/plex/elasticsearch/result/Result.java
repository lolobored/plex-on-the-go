package org.lolobored.plex.elasticsearch.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Result is the main class for the result retrieved from a search in Elastic Search
 * In the end we are mainly interested into the Source fields of it
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
	private Hits hits;

}
