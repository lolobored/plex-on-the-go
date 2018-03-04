
package org.lolobored.plex.objects.mediacontainer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Directory {
	private String key;
	private String type;
	private String title;
	@JsonProperty("Location")
	private List<DirectoryLocation> location;


}
