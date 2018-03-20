package org.lolobored.plex.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate startDate;
	private List<String> genres;
	private List<String> actors;



}
