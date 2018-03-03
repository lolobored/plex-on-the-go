package org.lolobored.plex.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sort {

	@JsonProperty("title.keyword")
	private String title;

	@JsonIgnore
	public void setTitleSort(){
		title = "asc";
	}

	public String getTitle() {
		return title;
	}
}
