package org.lolobored.plex.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Search is supposed to be used in Bool search in elastic search
 * It's the main object for these searches
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Search {

	private Query query;
	private Integer from;
	private Integer size;
	private List<Sort> sort;

	/**
	 * Retrieve the query
	 * @return
	 */
	public Query getQuery() {
		return query;
	}

	/**
	 * Sets the query for the search
	 * @param query
	 */
	public void setQuery(Query query) {
		this.query = query;
	}

	/**
	 * Return the initial index
	 * @return
	 */
	public Integer getFrom() {
		return from;
	}

	/**
	 * Sets the initial index
	 * @param from
	 */
	public void setFrom(Integer from) {
		this.from = from;
	}

	/**
	 * Returns the number of result wanted
	 * @return
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * Sets the number of results wanted
	 * @param size
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * Gets the sort options
	 * @return
	 */
	public List<Sort> getSort() {
		return sort;
	}

	/**
	 * Sets the sort options
	 * @param sort
	 */
	public void setSort(List<Sort> sort) {
		this.sort = sort;
	}
}
