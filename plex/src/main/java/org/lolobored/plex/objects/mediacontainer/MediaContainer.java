
package org.lolobored.plex.objects.mediacontainer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.lolobored.plex.objects.metadata.Metadata;

import java.math.BigInteger;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaContainer {
	private BigInteger size;
	private Boolean allowSync;
	private String title1;
	@JsonProperty("Directory")
	private List<Directory> directory;
	@JsonProperty("Metadata")
	private List<Metadata> metadata;


}
