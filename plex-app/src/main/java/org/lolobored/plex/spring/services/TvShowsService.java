package org.lolobored.plex.spring.services;

import org.lolobored.http.HttpException;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.elasticsearch.search.SearchRequest;

import java.util.List;

public interface TvShowsService {

	List<Media> getTvShows(String user);

	List<Media> searchTvShows(String username, SearchRequest searchRequest) throws HttpException;

	List<String> getTvShowsList();
}
