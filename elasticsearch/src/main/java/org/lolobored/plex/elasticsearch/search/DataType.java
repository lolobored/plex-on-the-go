package org.lolobored.plex.elasticsearch.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "datatype")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataType {

	@JsonIgnore
	public static final String YEAR = "year";
	@JsonIgnore
	public static final String TV_SHOW = "tvshow";
	@JsonIgnore
	public static final String GENRE = "genre";

	@Id
	private String id;
	private String type;
	private String value;
}
