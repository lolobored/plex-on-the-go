package org.lolobored.plex.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.lolobored.plex.elasticsearch.filters.Filter;
import org.lolobored.plex.model.Media;

/**
 * Should is supposed to be used in Bool search in elastic search
 * It represents an OR condition
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Should {

	@JsonProperty("term")
	private Filter filter;

	private Bool bool;

	/**
	 * Retrieve the single should filter
	 * @return
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * Sets the single should filter
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
	 * Sets the Bool additional filter (used to combine and and or)
	 */
	public void setBool(Bool bool) {
		this.bool = bool;
	}
}
