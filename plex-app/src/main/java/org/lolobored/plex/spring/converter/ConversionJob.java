package org.lolobored.plex.spring.converter;

import lombok.Data;
import org.lolobored.plex.spring.models.Conversion;

@Data
public class ConversionJob {

	private Conversion conversion;
	private ConversionProgress conversionProgress;
}
