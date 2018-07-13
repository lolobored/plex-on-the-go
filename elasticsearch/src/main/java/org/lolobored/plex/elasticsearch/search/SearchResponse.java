package org.lolobored.plex.elasticsearch.search;

import lombok.Data;
import org.lolobored.plex.model.Media;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResponse {
	private BigInteger pageNumber;
	private BigInteger total;
	private List<Media> results= new ArrayList<>();
}
