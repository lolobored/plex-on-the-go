package org.lolobored.plex.elasticsearch.search;

import lombok.Data;
import org.lolobored.plex.model.Media;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class SearchRequest extends Media {
	private Integer resultPerPage;
	private Integer pageNumber;
	private Integer yearFrom;
	private Integer yearTo;
	private List<String> showTitles= new ArrayList<>();
}
