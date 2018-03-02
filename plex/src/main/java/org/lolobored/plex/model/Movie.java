package org.lolobored.plex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie extends Media {

	public Movie() {
		type = MOVIE_TYPE;
	}

}

