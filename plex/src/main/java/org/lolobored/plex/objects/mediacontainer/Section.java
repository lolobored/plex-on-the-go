
package org.lolobored.plex.objects.mediacontainer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Section {
	@JsonProperty("MediaContainer")
	private MediaContainer mediaContainer;


}
