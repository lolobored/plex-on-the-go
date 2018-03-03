package org.lolobored.plex.elasticsearch.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
	private Hits hits;

	public Hits getHits() {
		return hits;
	}

	public void setHits(Hits hits) {
		this.hits = hits;
	}
}
