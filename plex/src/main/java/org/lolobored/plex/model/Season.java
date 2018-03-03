package org.lolobored.plex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
