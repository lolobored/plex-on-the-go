package org.lolobored.plex.spring.models;

import lombok.Data;
import org.lolobored.plex.model.Media;

import java.util.ArrayList;
import java.util.List;

@Data
public class Search extends Media {
	private Integer yearFrom;
	private Integer yearTo;
	private List<String> showTitles= new ArrayList<>();
}
