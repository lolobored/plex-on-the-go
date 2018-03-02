package org.lolobored.plex.elasticsearch.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.lolobored.plex.model.Episode;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hits {

	@JsonProperty("hits")
	private List<Source> list;


}
