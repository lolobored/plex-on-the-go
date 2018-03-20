package org.lolobored.plex.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * A Media is the object, either a Movie or a TV Episode
 * that contains all the information for the media (title, genre, year...)
 * Ultimately this is the object that will get stored into Elastic Search
 */
@Data
@Document(indexName = "media")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Media{

	@JsonIgnore
	public static final String EPISODE_TYPE = "episode";
	@JsonIgnore
	public static final String MOVIE_TYPE = "movie";

	@Id
	private String id;
	private String plexId;
	private String type;
	private String library;
	private String user;
	private String title;
	private String fileLocation;
	private Integer year;
	private BigDecimal rating;
	private String summary;
	private List<String> genres;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate releaseDate;
	private List<String> directors;
	private List<String> actors;
	private List<String> writers;
	// tvshows only
	private Show show;
	private Season season;
	private Integer episode;

	/**
	 * Returns true if it's an episode
	 * @return
	 */
	@JsonIgnore
	public boolean isEpisode(){
		return EPISODE_TYPE.equals(type);
	}

	/**
	 * Returns true if it's a movie
	 * @return
	 */
	@JsonIgnore
	public boolean isMovie(){
		return MOVIE_TYPE.equals(type);
	}
}

