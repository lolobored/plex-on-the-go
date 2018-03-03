package org.lolobored.plex.elasticsearch.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hits {

	@JsonProperty("hits")
	private List<Source> list;


	public List<Source> getList() {
		return list;
	}

	public void setList(List<Source> list) {
		this.list = list;
	}
}
