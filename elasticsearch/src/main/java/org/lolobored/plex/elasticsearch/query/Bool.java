package org.lolobored.plex.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Bool is supposed to be used in Bool search in elastic search
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bool {

	// list of must (and condition)
	private List<Must> must;
	// list of should (or conditions)
	private List<Should> should;

	/**
	 * Gets the list of Must (and conditions for the query)
	 * @return
	 */
	public List<Must> getMust() {

		return must;
	}

	/**
	 * Sets the list of Must (and conditions for the query)
	 * @param must
	 */
	public void setMust(List<Must> must) {
		this.must = must;
	}

	/**
	 * Gets the list of Should (or conditions for the query)
	 * @return
	 */
	public List<Should> getShould() {
		return should;
	}

	/**
	 * Sets the list of Should (or conditions for the query)
	 * @param should
	 */
	public void setShould(List<Should> should) {
		this.should = should;
	}
}
