package org.lolobored.plex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The season object contains every information for a particular season of a tvshow
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Season {
	private Integer seasonNumber;
	private String summary;


	/**
	 * @return the seasonNumber
	 */
	public Integer getSeasonNumber() {
		return seasonNumber;
	}

	/**
	 * @param seasonNumber the seasonNumber to set
	 */
	public void setSeasonNumber(Integer seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	/**
	 * Gets the summary of the season
	 * @return
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the summary of the season
	 * @param summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
}
