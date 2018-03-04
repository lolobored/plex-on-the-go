
package org.lolobored.plex.objects.metadata;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Data
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
	@JsonProperty("Genre")
	private List<Genre> genre;
	@JsonProperty("Media")
	private List<MediaPlex> media;
	@JsonProperty("Director")
	private List<Director> director;
	@JsonProperty("Writer")
	private List<Writer> writer;


}
