package org.lolobored.plex.spring.services;

import org.lolobored.plex.elasticsearch.search.SearchRequest;
import org.lolobored.plex.elasticsearch.search.SearchResponse;

import java.util.List;

public interface MoviesService {

    List<String> getGenres();
    List<String> getYears();
    SearchResponse searchMovies(String username, SearchRequest searchRequest);

}
