package org.lolobored.plex.elasticsearch.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.lolobored.plex.model.Media;

/**
 * Source is the representation of the object stored in ElasticSearch plus additional information
 * However in our case we just need the actual object
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {

	@JsonProperty("_source")
	private Media media;

}
