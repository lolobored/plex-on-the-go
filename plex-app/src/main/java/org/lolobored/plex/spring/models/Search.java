package org.lolobored.plex.spring.models;

import lombok.Data;
import org.lolobored.plex.model.Media;

@Data
public class Search extends Media {
	private Integer yearFrom;
	private Integer yearTo;
}
