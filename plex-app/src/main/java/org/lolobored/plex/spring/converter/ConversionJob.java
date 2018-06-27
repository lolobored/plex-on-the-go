package org.lolobored.plex.spring.converter;

import lombok.Data;
import org.lolobored.plex.spring.models.PendingConversion;

@Data
public class ConversionJob {

	private PendingConversion pendingConversion;
	private ConversionProgress conversionProgress;
}
