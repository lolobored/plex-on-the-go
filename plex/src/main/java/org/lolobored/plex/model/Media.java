package org.lolobored.plex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * A Media is the object, either a Movie or a TV Episode
 * that contains all the information for the media (title, genre, year...)
 * Ultimately this is the object that will get stored into Elastic Search
 */
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
	private LocalDate releaseDate;
	private List<String> directors;
	private List<String> actors;
	private List<String> writers;
	// tvshows only
	private Show show;
	private Season season;
	private Integer episode;

	/**
	 * Returns the type (either movie or episode)
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type (either movie or episode)
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the library name (Plex can contain multiple TVShow libraries)
	 * @return
	 */
	public String getLibrary() {
		return library;
	}

	/**
	 * Sets the library name (Plex can contain multiple Movies libraries)
	 * @param library
	 */
	public void setLibrary(String library) {
		this.library = library;
	}

	/**
	 * Gets the user to which that item belong to
	 * @return
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the user to which that item belongs to
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Gets the item's title
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the item's title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the file location
	 * @return
	 */
	public String getFileLocation() {
		return fileLocation;
	}

	/**
	 * Sets the file location
	 * @param fileLocation
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	/**
	 * Get the year when the item was released
	 * @return
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * Sets the year when the item was released
	 * @param year
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * Get the rating (over 10)
	 * @return
	 */
	public BigDecimal getRating() {
		return rating;
	}

	/**
	 * Sets the rating (over 10)
	 * @param rating
	 */
	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	/**
	 * Gets the item summary
	 * @return
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the item summary
	 * @param summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * Gets the list of Genres
	 * @return
	 */
	public List<String> getGenres() {
		return genres;
	}

	/**
	 * Sets the list of Genre
	 * @param genres
	 */
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	/**
	 * Get the release date
	 * @return
	 */
	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Sets the release date
	 * @param releaseDate
	 */
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * Gets the list of directors
	 * @return
	 */
	public List<String> getDirectors() {
		return directors;
	}

	/**
	 * Sets the list of directors
	 * @param directors
	 */
	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}

	/**
	 * Get the list of actors
	 * @return
	 */
	public List<String> getActors() {
		return actors;
	}

	/**
	 * Sets the list of Actors
	 * @param actors
	 */
	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	/**
	 * Get the List of Writers
	 * @return
	 */
	public List<String> getWriters() {
		return writers;
	}

	/**
	 * Sets the list of Writers
	 * @param writers
	 */
	public void setWriters(List<String> writers) {
		this.writers = writers;
	}

	/**
	 * Get the show for a tv episode
	 * @return
	 */
	public Show getShow() {
		return show;
	}

	/**
	 * Sets the show for a tv episode
	 * @param show
	 */
	public void setShow(Show show) {
		this.show = show;
	}

	/**
	 * Gets the season
	 * @return
	 */
	public Season getSeason() {
		return season;
	}

	/**
	 * Sets the season
	 * @param season
	 */
	public void setSeason(Season season) {
		this.season = season;
	}

	/**
	 * Get the episode number
	 * @return
	 */
	public Integer getEpisode() {
		return episode;
	}

	/**
	 * Sets the episode number
	 * @param episode
	 */
	public void setEpisode(Integer episode) {
		this.episode = episode;
	}

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

