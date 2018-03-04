package org.lolobored.plex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Contains every information about a tvshow in Plex
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Show {

	private String showTitle;
	private String summary;
	private String contentRating;
	private String studio;
	private BigDecimal rating;
	private Integer year;
	private LocalDate startDate;
	private List<String> genres;
	private List<String> actors;



}
