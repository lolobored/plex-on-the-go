package org.lolobored.plex.elasticsearch.filters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Filter is supposed to be used in Bool search in elastic search
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html">Bool Query</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Filter {

	private String user;
	private String genre;
	private String type;
	@JsonProperty("season.seasonNumber")
	private Integer season;
	@JsonProperty("show.showTitle")
	private String showTitle;

	/**
	 * Create a filter per Plex library username
	 * @return user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Set the user filter
	 * @param user the name of the Plex library user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * get the genre to filter on
	 * @return
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Set the genre filter
	 * @param genre the name of the genre we want to filter on
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * get the season number to filter on
	 * @return
	 */
	public Integer getSeason() {
		return season;
	}

	/**
	 * Set the season filter
	 * @param season the number for the season to filter on
	 */
	public void setSeason(Integer season) {
		this.season = season;
	}

	/**
	 * get the show title to filter on
	 * @return showTitle
	 */
	public String getShowTitle() {
		return showTitle;
	}

	/**
	 * Set the show filter
	 * @param showTitle the name of the show to filter on
	 */
	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}

	/**
	 * get the type to filter on
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the type filter
	 * @param type the type (movie/episode) to filter on
	 */
	public void setType(String type) {
		this.type = type;
	}
}
