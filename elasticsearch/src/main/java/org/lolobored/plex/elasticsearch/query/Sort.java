package org.lolobored.plex.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Sort is used in Bool search in elastic search
 * It defines sort by fields
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
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
