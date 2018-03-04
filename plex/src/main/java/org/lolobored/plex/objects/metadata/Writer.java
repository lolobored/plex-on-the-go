
package org.lolobored.plex.objects.metadata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigInteger;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Writer {
	private BigInteger id;
	private String filter;
	private String tag;


}
