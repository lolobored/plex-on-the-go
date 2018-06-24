package org.lolobored.plex.spring.converter;

import lombok.Data;

@Data
public class ConversionProgress {
	private double percentage;
	private long elapsedTime;
	private long remainingTime;
	private float speed;

}
