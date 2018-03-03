package org.lolobored.plex.elasticsearch.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.lolobored.plex.model.Media;

/**
 * Source is the representation of the object stored in ElasticSearch plus additional information
 * However in our case we just need the actual object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {

	@JsonProperty("_source")
	private Media media;

	/**
	 * Returns the media as being stored into Elastic search
	 * @return
	 */
	public Media getMedia() {
		return media;
	}

	/**
	 * Sets the media as being stored in Elastic search
	 * @param media
	 */
	public void setMedia(Media media) {
		this.media = media;
	}
}
