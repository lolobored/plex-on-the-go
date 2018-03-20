package org.lolobored.plex.elasticsearch.filters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Filter is supposed to be used in Bool search in elastic search
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Filter {

	private String user;
	private String genre;
	private String type;
	@JsonProperty("season.seasonNumber")
	private Integer season;
	@JsonProperty("show.showTitle")
	private String showTitle;
	private String fileLocation;

}
