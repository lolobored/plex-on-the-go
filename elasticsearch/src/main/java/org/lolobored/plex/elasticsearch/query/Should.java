package org.lolobored.plex.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.lolobored.plex.elasticsearch.filters.Filter;
import org.lolobored.plex.model.Media;

/**
 * Should is supposed to be used in Bool search in elastic search
 * It represents an OR condition
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Should {

	@JsonProperty("term")
	private Filter filter;

	private Bool bool;


}
