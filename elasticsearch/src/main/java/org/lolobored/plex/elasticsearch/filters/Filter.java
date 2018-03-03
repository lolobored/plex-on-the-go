package org.lolobored.plex.elasticsearch.filters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter {

	private String user;
	private String genre;
	private String type;
	@JsonProperty("season.seasonNumber")
	private Integer season;
	@JsonProperty("show.showTitle")
	private String showTitle;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Integer getSeason() {
		return season;
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	public String getShowTitle() {
		return showTitle;
	}

	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
