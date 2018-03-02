/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lolobored.plex.objects.metadata;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author laurentlaborde
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {
	private String ratingKey;
	private String key;
	private String parentRatingKey;
	private String grandparentRatingKey;
	private String type;
	private String title;
	private String grandparentKey;
	private String parentKey;
	private String grandparentTitle;
	private String parentTitle;
	private String summary;
	private Integer year;
	private String studio;
	private String contentRating;
	private Integer index;
	private Integer parentIndex;
	private BigDecimal rating;
	private BigInteger viewCount;
	private String thumb;
	private String art;
	private String parentThumb;
	private String grandparentThumb;
	private String grandparentArt;
	private String grandparentTheme;
	private String originallyAvailableAt;
	@JsonProperty("Media")
	private List<MediaPlex> media;
	@JsonProperty("Director")
	private List<Director> director;
	@JsonProperty("Writer")
	private List<Writer> writer;

	/**
	 * @return the ratingKey
	 */
	public String getRatingKey() {
		return ratingKey;
	}

	/**
	 * @param ratingKey the ratingKey to set
	 */
	public void setRatingKey(String ratingKey) {
		this.ratingKey = ratingKey;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the parentRatingKey
	 */
	public String getParentRatingKey() {
		return parentRatingKey;
	}

	/**
	 * @param parentRatingKey the parentRatingKey to set
	 */
	public void setParentRatingKey(String parentRatingKey) {
		this.parentRatingKey = parentRatingKey;
	}

	/**
	 * @return the grandparentRatingKey
	 */
	public String getGrandparentRatingKey() {
		return grandparentRatingKey;
	}

	/**
	 * @param grandparentRatingKey the grandparentRatingKey to set
	 */
	public void setGrandparentRatingKey(String grandparentRatingKey) {
		this.grandparentRatingKey = grandparentRatingKey;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the grandparentKey
	 */
	public String getGrandparentKey() {
		return grandparentKey;
	}

	/**
	 * @param grandparentKey the grandparentKey to set
	 */
	public void setGrandparentKey(String grandparentKey) {
		this.grandparentKey = grandparentKey;
	}

	/**
	 * @return the parentKey
	 */
	public String getParentKey() {
		return parentKey;
	}

	/**
	 * @param parentKey the parentKey to set
	 */
	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	/**
	 * @return the grandparentTitle
	 */
	public String getGrandparentTitle() {
		return grandparentTitle;
	}

	/**
	 * @param grandparentTitle the grandparentTitle to set
	 */
	public void setGrandparentTitle(String grandparentTitle) {
		this.grandparentTitle = grandparentTitle;
	}

	/**
	 * @return the parentTitle
	 */
	public String getParentTitle() {
		return parentTitle;
	}

	/**
	 * @param parentTitle the parentTitle to set
	 */
	public void setParentTitle(String parentTitle) {
		this.parentTitle = parentTitle;
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
	 * @return the viewCount
	 */
	public BigInteger getViewCount() {
		return viewCount;
	}

	/**
	 * @param viewCount the viewCount to set
	 */
	public void setViewCount(BigInteger viewCount) {
		this.viewCount = viewCount;
	}

	/**
	 * @return the thumb
	 */
	public String getThumb() {
		return thumb;
	}

	/**
	 * @param thumb the thumb to set
	 */
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	/**
	 * @return the art
	 */
	public String getArt() {
		return art;
	}

	/**
	 * @param art the art to set
	 */
	public void setArt(String art) {
		this.art = art;
	}

	/**
	 * @return the parentThumb
	 */
	public String getParentThumb() {
		return parentThumb;
	}

	/**
	 * @param parentThumb the parentThumb to set
	 */
	public void setParentThumb(String parentThumb) {
		this.parentThumb = parentThumb;
	}

	/**
	 * @return the grandparentThumb
	 */
	public String getGrandparentThumb() {
		return grandparentThumb;
	}

	/**
	 * @param grandparentThumb the grandparentThumb to set
	 */
	public void setGrandparentThumb(String grandparentThumb) {
		this.grandparentThumb = grandparentThumb;
	}

	/**
	 * @return the grandparentArt
	 */
	public String getGrandparentArt() {
		return grandparentArt;
	}

	/**
	 * @param grandparentArt the grandparentArt to set
	 */
	public void setGrandparentArt(String grandparentArt) {
		this.grandparentArt = grandparentArt;
	}

	/**
	 * @return the grandparentTheme
	 */
	public String getGrandparentTheme() {
		return grandparentTheme;
	}

	/**
	 * @param grandparentTheme the grandparentTheme to set
	 */
	public void setGrandparentTheme(String grandparentTheme) {
		this.grandparentTheme = grandparentTheme;
	}

	/**
	 * @return the originallyAvailableAt
	 */
	public String getOriginallyAvailableAt() {
		return originallyAvailableAt;
	}

	/**
	 * @param originallyAvailableAt the originallyAvailableAt to set
	 */
	public void setOriginallyAvailableAt(String originallyAvailableAt) {
		this.originallyAvailableAt = originallyAvailableAt;
	}

	/**
	 * @return the Media
	 */
	public List<MediaPlex> getMedia() {
		return media;
	}

	/**
	 * @param Media the Media to set
	 */
	public void setMedia(List<MediaPlex> media) {
		this.media = media;
	}

	/**
	 * @return the Director
	 */
	public List<Director> getDirector() {
		return director;
	}

	/**
	 * @param Director the Director to set
	 */
	public void setDirector(List<Director> director) {
		this.director = director;
	}

	/**
	 * @return the Writer
	 */
	public List<Writer> getWriter() {
		return writer;
	}

	/**
	 * @param Writer the Writer to set
	 */
	public void setWriter(List<Writer> writer) {
		this.writer = writer;
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
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @return the parentIndex
	 */
	public Integer getParentIndex() {
		return parentIndex;
	}

	/**
	 * @param parentIndex the parentIndex to set
	 */
	public void setParentIndex(Integer parentIndex) {
		this.parentIndex = parentIndex;
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

}
