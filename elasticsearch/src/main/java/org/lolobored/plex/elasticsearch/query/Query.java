package org.lolobored.plex.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Query {

	private Bool bool;

	public Query() {
	}

	public Bool getBool() {
		return bool;
	}

	public void setBool(Bool bool) {
		this.bool = bool;
	}

}
