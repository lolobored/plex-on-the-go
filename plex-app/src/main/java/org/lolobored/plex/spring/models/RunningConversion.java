package org.lolobored.plex.spring.models;

import lombok.Data;

@Data
public class RunningConversion {
	private String title;
	private Double percentage=0.0;
	private String estimatedRemaining;
	private String elapsedTime;
}
