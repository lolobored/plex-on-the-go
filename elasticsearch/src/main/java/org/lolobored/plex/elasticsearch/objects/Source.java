package org.lolobored.plex.elasticsearch.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.lolobored.plex.model.Episode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {

	@JsonProperty("_source")
	private Episode episode;

	public Episode getEpisode() {
		return episode;
	}

	public void setEpisode(Episode episode) {
		this.episode = episode;
	}
}
