package org.lolobored.plex.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Bool {

	private List<Must> must;
	private List<Should> should;

	public List<Must> getMust() {
		return must;
	}

	public void setMust(List<Must> must) {
		this.must = must;
	}
}
