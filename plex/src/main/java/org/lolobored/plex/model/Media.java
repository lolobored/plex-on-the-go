package org.lolobored.plex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Media {

	@JsonIgnore
	public final String EPISODE_TYPE = "episode";
	@JsonIgnore
	public final String MOVIE_TYPE = "movie";

	protected String type;
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

	protected Media() {

	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the fileLocation
	 */
	public String getFileLocation() {
		return fileLocation;
	}

	/**
	 * @param fileLocation the fileLocation to set
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * @return the rating
	 */
	public BigDecimal getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the genres
	 */
	public List<String> getGenres() {
		return genres;
	}

	/**
	 * @param genres the genres to set
	 */
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the directors
	 */
	public List<String> getDirectors() {
		return directors;
	}

	/**
	 * @param directors the directors to set
	 */
	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}

	/**
	 * @return the actors
	 */
	public List<String> getActors() {
		return actors;
	}

	/**
	 * @param actors the actors to set
	 */
	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	/**
	 * @return the writers
	 */
	public List<String> getWriters() {
		return writers;
	}

	/**
	 * @param writers the writers to set
	 */
	public void setWriters(List<String> writers) {
		this.writers = writers;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public String getLibrary() {
		return library;
	}
}

