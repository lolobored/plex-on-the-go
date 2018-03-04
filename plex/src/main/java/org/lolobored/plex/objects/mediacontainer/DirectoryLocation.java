
package org.lolobored.plex.objects.mediacontainer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigInteger;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectoryLocation {
	private BigInteger id;
	private String path;


}
