package org.lolobored.plex.elasticsearch.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Result is the main class for the result retrieved from a search in Elastic Search
 * In the end we are mainly interested into the Source fields of it
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
	private Hits hits;

	/**
	 * Returns the hits for the search
	 * @return
	 */
	public Hits getHits() {
		return hits;
	}

	/**
	 * Sets the hits for the search results
	 * @param hits
	 */
	public void setHits(Hits hits) {
		this.hits = hits;
	}
}
