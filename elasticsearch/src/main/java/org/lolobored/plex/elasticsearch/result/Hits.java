package org.lolobored.plex.elasticsearch.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Hits objects is the actual object containing the list of objects returned by the query.
 * Query are initialized using the Bool query here
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hits {

	@JsonProperty("hits")
	private List<Source> list;

}
