package org.lolobored.plex.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Bool is supposed to be used in Bool search in elastic search
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Bool {

	// list of must (and condition)
	private List<Must> must;
	// list of should (or conditions)
	private List<Should> should;
}
