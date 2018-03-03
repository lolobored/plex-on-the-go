package org.lolobored.plex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Media{

	@JsonIgnore
	public static final String EPISODE_TYPE = "episode";
	@JsonIgnore
	public static final String MOVIE_TYPE = "movie";

	private String type;
	private String library;
	private String user;
	private String title;
	private String fileLocation;
	private Integer year;
	private BigDecimal rating;
	private String summary;
	private List<String> genres;
	private LocalDate startDate;
	private List<String> directors;
	private List<String> actors;
	private List<String> writers;
	// tvshows only
	private Show show;
	private Season season;
	private Integer episode;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLibrary() {
		return library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public List<String> getDirectors() {
		return directors;
	}

	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public List<String> getWriters() {
		return writers;
	}

	public void setWriters(List<String> writers) {
		this.writers = writers;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public Integer getEpisode() {
		return episode;
	}

	public void setEpisode(Integer episode) {
		this.episode = episode;
	}

	@JsonIgnore
	public boolean isEpisode(){
		return EPISODE_TYPE.equals(type);
	}

	@JsonIgnore
	public boolean isMovie(){
		return EPISODE_TYPE.equals(type);
	}
}

