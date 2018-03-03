package org.lolobored.plex.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.lolobored.plex.elasticsearch.filters.Filter;

/**
 * Bool is supposed to be used in Bool search in elastic search
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Must {

	@JsonProperty("term")
	private Filter filter;

	private Bool bool;

	/**
	 * Retrieve the single must filter
	 * @return
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * Sets the must filter
	 * @param filter
	 */
	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	/**
	 * Gets the Bool additional filter (used to combine and and or)
	 * @return
	 */
	public Bool getBool() {
		return bool;
	}

	/**
	 * Set a Bool additional filter (used to combine and and or)
	 * @param bool
	 */
	public void setBool(Bool bool) {
		this.bool = bool;
	}
}
