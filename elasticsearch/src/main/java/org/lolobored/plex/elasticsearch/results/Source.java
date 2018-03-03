package org.lolobored.plex.elasticsearch.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.lolobored.plex.model.Media;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {

	@JsonProperty("_source")
	private Media media;

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}
}
