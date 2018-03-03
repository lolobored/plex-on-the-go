package org.lolobored.plex.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Query is supposed to be used in Bool search in elastic search
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Query {

	private Bool bool;

	/**
	 * Retrieve the bool object
	 * @return
	 */
	public Bool getBool() {
		return bool;
	}

	/**
	 * Sets the bool object
	 * @param bool
	 */
	public void setBool(Bool bool) {
		this.bool = bool;
	}

}
