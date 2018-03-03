package org.lolobored.plex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Contains every information about a tvshow in Plex
 */
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
	 * @return the contentRating
	 */
	public String getContentRating() {
		return contentRating;
	}

	/**
	 * @param contentRating the contentRating to set
	 */
	public void setContentRating(String contentRating) {
		this.contentRating = contentRating;
	}

	/**
	 * @return the studio
	 */
	public String getStudio() {
		return studio;
	}

	/**
	 * @param studio the studio to set
	 */
	public void setStudio(String studio) {
		this.studio = studio;
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
	 * @return the showTitle
	 */
	public String getShowTitle() {
		return showTitle;
	}

	/**
	 * @param showTitle the showTitle to set
	 */
	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}
}
