package org.lolobored.plex.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Search is supposed to be used in Bool search in elastic search
 * It's the main object for these searches
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Search {

	private Query query;
	private Integer from;
	private Integer size;
	private List<Sort> sort;


}
